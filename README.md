# Description

The sample shows the way to upload files to azure blob storage from the frontend.
The backend part is written on Java (Spring Boot 2.4.1)
The frontend part is written on Angular v11

# App configuration

This paramateres should be configured in file-storage/src/main/resources/application.yml

``` yml
app:
  azure-account:
    name: <<your account name>>
    key: <<your account pass>>
  offsetHours: 24
```
# Running

1. Run "file-storage" application
2. Run "frontend"
