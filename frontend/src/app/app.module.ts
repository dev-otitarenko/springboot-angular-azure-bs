import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { StartComponent } from './components/start/start.component';
import {HttpClientModule} from "@angular/common/http";
import {TableModule} from "primeng/table";
import {ButtonModule} from "primeng/button";
import {RippleModule} from "primeng/ripple";
import {TooltipModule} from "primeng/tooltip";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import { UploadFileComponent } from './components/upload-file/upload-file.component';
import {DialogModule} from "primeng/dialog";
import {ProgressBarModule} from "primeng/progressbar";
import {FileUploadModule} from "primeng/fileupload";
import {ToastModule} from "primeng/toast";
import {MessageService} from "primeng/api";
import {ToolbarModule} from "primeng/toolbar";

@NgModule({
  declarations: [
    AppComponent,
    StartComponent,
    UploadFileComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    AppRoutingModule,
    ToastModule, ToolbarModule,
    DialogModule, ProgressBarModule, FileUploadModule,
    TableModule, ButtonModule, RippleModule, TooltipModule
  ],
  providers: [
    MessageService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
