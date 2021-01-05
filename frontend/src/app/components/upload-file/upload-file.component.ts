import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {FileUpload} from "primeng/fileupload";
import {HttpEventType} from "@angular/common/http";
import {map, retry} from "rxjs/operators";
import {FilesService} from "../../services/files.service";
import {Base64} from 'js-base64';
import * as Base64js from 'base64-js';
import {MessageService} from "primeng/api";
import {UIUtils} from "../../utils/Utils";

@Component({
  selector: 'app-upload-file',
  templateUrl: './upload-file.component.html',
  styleUrls: ['./upload-file.component.css']
})
export class UploadFileComponent implements OnInit {
  @Input() containerName: string;
  @Output() onSuccess: EventEmitter<any> = new EventEmitter<any>();
  @Output() onFailed: EventEmitter<any> = new EventEmitter<any>();
  @ViewChild('fileCmp', {static: false})

  visible: boolean;

  private _fileName: string;
  private fileCmp: FileUpload;
  private _status: number; // 0 - before uploading, 1 - uploading, 2 - succeed, 3 - failed
  private _uploadProgress: number;

  constructor(private dataService: FilesService,
              private messageService: MessageService) {
  }

  ngOnInit(): void {
  }

  show() {
    this._status = 0;
    this._uploadProgress = 0;
    this.visible = true;
    this.fileCmp.clear();
  }

  // @ts-ignore
  fileUploader(event) {
    const fileList: FileList = event.files;
    if (fileList.length === 1) {
      const file: File = fileList[0];
      const fileType = file.type || 'application/octet-stream';
      this._fileName = file.name;
      this._status = 1;
      this.containerName = `sample-${this.containerName}`;

      this.dataService.beforeUpload(this.containerName)
        .subscribe(
          id => {
            const fr = new FileReader();
            fr.onload = () => {
              this.upload(this.containerName, id, file.name, fileType, <ArrayBuffer>fr.result);
            };
            fr.readAsArrayBuffer(file);
          },
          error => {
            this.messageService.add(UIUtils.getMessageVar(error));

            this._status = 3;
            this.onFailed.emit();

            this.finish(false);
          });
    }
  }

  private async upload(idContainer: string, idFile: string, fileName: string, fileType: string, fileData: ArrayBuffer) {
    const chunkSize = 1024 * 1024;
    let chunkInd = 1;
    const base64BlockIds: string[] = [];
    let cntCompleted = 0;
    const fileLength = fileData.byteLength;
    const totalLength = fileLength * 1.35;
    let uploadedLength = 0;
    const chunkCnt = Math.ceil(fileLength / chunkSize);
    for (let offset = 0; offset < fileLength; offset += chunkSize) {
      const chunk = fileData.slice(offset, offset + chunkSize);
      const base64BlockId = Base64.encode((chunkInd + '').padStart(10, '0'));
      base64BlockIds.push(base64BlockId);
      let chunkLength: number;
      try {
        await this.dataService.uploadFile(idContainer, idFile, {
          base64BlockId: base64BlockId,
          data: Base64js.fromByteArray(new Uint8Array(chunk))
        }).pipe(
          retry(3),
          map(
            (event: any) => {
              if (event.type === HttpEventType.UploadProgress) {
                this._uploadProgress = Math.round((100 / totalLength) * (uploadedLength + event.loaded));
                chunkLength = event.total;
              } else if (event.type === HttpEventType.Response) {
                uploadedLength += chunkLength;
              }
              return event;
            }
          )).toPromise();

          cntCompleted++;
          if (cntCompleted === chunkCnt) {
            this.dataService.completeUpload(idContainer, idFile, {name: fileName, type: fileType, base64BlockIds: base64BlockIds}).subscribe(
              res => {
                this.finish(this._status != 3);
                this.visible = false;

                this.onSuccess.emit({id: idContainer, name: fileName, type: fileType, blocks: base64BlockIds.join(',')});
              }, error => {
                this.messageService.add(UIUtils.getMessageVar(error));
              }
            )
          }
      } catch (error) {
        this.messageService.add(UIUtils.getMessageVar(error));

        this._status = 3;
        this.onFailed.emit();
      }
      chunkInd++;
    }
  }

  finish(succeed: boolean) {
    this._uploadProgress = 100;
    this._status = succeed ? 2 : 3;
  }

  get fileName(): string {
    return this._fileName;
  }

  get status(): number {
    return this._status;
  }

  get uploadProgress(): number {
    return this._uploadProgress;
  }
}
