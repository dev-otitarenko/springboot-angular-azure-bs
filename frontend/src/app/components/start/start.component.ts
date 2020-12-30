import {Component, OnInit, ViewChild} from '@angular/core';
import {FilesService} from "../../services/files.service";
import * as uuid from "uuid";
import {UploadFileComponent} from "../upload-file/upload-file.component";
import {MessageService} from "primeng/api";

@Component({
  selector: 'app-start',
  templateUrl: './start.component.html',
  styleUrls: ['./start.component.css']
})
export class StartComponent implements OnInit {
  idContainer: string = "";
  containers: any[] = [];
  selected: any;

  @ViewChild(UploadFileComponent, { static: false }) uploaderCmp;

  constructor(private filesService: FilesService,
              private messageService: MessageService) { }

  ngOnInit() {
    this.loadContainers();
  }

  showUpload() {
    this.idContainer = uuid.v4();
    this.uploaderCmp.show();
  }

  async remove() {
    const code = this.selected;
    const listOfFiles: any = await this.filesService.listOfFilesInContainer(code);
    console.log("ListOfBlobs", listOfFiles);

    for(const i in listOfFiles) {
      const idFile = listOfFiles[i];
      this.filesService.removeFile(code, idFile)
        .then((res) => { this.messageService.add({ severity: 'success', summary: 'Info', detail: 'File removed' }) });
    }

    this.filesService.removeContainer(code)
      .then((res) => {
        this.messageService.add({ severity: 'success', summary: 'Info', detail: 'Container removed' });
        this.loadContainers();
      });
  }

  async onRowSelect(data: any) {
    this.selected = data.data;
    const code = this.selected;
    const listOfFiles: any = await this.filesService.listOfFilesInContainer(code);
    console.log("onRowSelect", data, listOfFiles);
  }

  fileUploadSuccess(data: any) {
    console.log("fielUploadSuccess", data);

    this.loadContainers();
  }

  fileUploadFailed(event: any) {
    console.log("fielUploadFailed", event);
  }

  private loadContainers() {
    this.filesService.listOfContainers().then(data => this.containers = data);
  }
}
