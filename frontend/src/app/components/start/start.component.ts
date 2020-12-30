import {Component, OnInit, ViewChild} from '@angular/core';
import {FilesService} from "../../services/files.service";
import * as uuid from "uuid";
import {UploadFileComponent} from "../upload-file/upload-file.component";
import {MessageService} from "primeng/api";
import {Container} from "../../utils/Container";

@Component({
  selector: 'app-start',
  templateUrl: './start.component.html',
  styleUrls: ['./start.component.css']
})
export class StartComponent implements OnInit {
  idContainer: string = "";
  containers: Container[] = [];
  selected: Container;

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
    const code = this.selected.name;
    const listOfFiles: any = await this.filesService.listOfFilesInContainer(code);

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
    const code = this.selected.name;
    const listOfFiles: any = await this.filesService.listOfFilesInContainer(code);
    const url = (listOfFiles.length > 0) ? await this.filesService.getStorageLink(code, listOfFiles[0]) : "";

    console.log("onRowSelect", data, listOfFiles, url);
  }

  async open(event, row: Container) {
    const code = row.name;
    const listOfFiles: any = await this.filesService.listOfFilesInContainer(code);
    const url = (listOfFiles.length > 0) ? await this.filesService.getStorageLink(code, listOfFiles[0]) : "";

    //this._loading = true;
    (event as MouseEvent).preventDefault();
    if (url != "") window.open(""+ url, "_blank")
    // this.dataService.getLink(this.idProject, id)
    //   .pipe(finalize(() => this._loading = false))
    //   .subscribe(
    //     data => {
    //       window.open(data, '_blank');
    //     },
    //     error => {
    //       this.messageService.showMessage(error, 'Error receiving data');
    //     }
    //   );
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
