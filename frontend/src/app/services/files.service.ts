import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {MessageService} from "primeng/api";
import {UIUtils} from "../utils/Utils";

@Injectable({
  providedIn: 'root'
})
export class FilesService {
  constructor(private http: HttpClient,
              private messageService: MessageService) { }

  listOfContainers() {
    return this.http.get<any>('/api/bs/')
      .toPromise()
      .catch((error) => { this.messageService.add(UIUtils.getMessageVar(error)); })
      .then(data => { return data; });
  }

  removeContainer(iddoc: string) {
    return this.http.delete<any>(
        `/api/bs/${iddoc}`,
      {
                headers: new HttpHeaders({
                  'Content-Type': 'application/json'
                })
        })
        .toPromise()
        .catch((error) => { this.messageService.add(UIUtils.getMessageVar(error)); });
  }

  listOfFilesInContainer(iddoc: string) {
    return this.http.get(`/api/bs/${iddoc}/files`)
      .toPromise()
      .catch((error) => { this.messageService.add(UIUtils.getMessageVar(error)); })
      .then(data => { return data; });
  }

  removeFile(iddoc: string, id: string) {
    return this.http.delete(
        `/api/bs/${iddoc}/files/${id}`,
      {
          headers: new HttpHeaders({
            'Content-Type': 'application/json'
          })
        })
        .toPromise()
        .catch((error) => { this.messageService.add(UIUtils.getMessageVar(error)); });
  }

  getStorageLink(iddoc: string, id: string): Observable<string> {
    return this.http.get(`/api/bs/${iddoc}/files/${id}/link`, {responseType: 'text'});
  }

  beforeUpload(iddoc: string): Observable<string> {
    return this.http.post(`/api/bs/${iddoc}/files/before-upload`, null, {responseType: 'text'});
  }

  uploadFile(iddoc: string, id: string, data: any): Observable<any> {
    return this.http.put<any>(`/api/bs/${iddoc}/files/${id}`,
      data,
      { observe: 'events', reportProgress: true } as any);
  }

  completeUpload(iddoc: string, id: string, data: any): Observable<any> {
    return this.http.post<any>(`/api/bs/${iddoc}/files/${id}`,
      data,
      { observe: 'events', reportProgress: true } as any);
  }
}
