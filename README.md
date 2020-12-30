# springboot-angular-upload-to-azure
The sample showing the way to upload files to azure blob storage

# spring boot app configuration
This paramateres should be set

``` yml
app:
  azure-account:
    name: <<your account name>>
    key: <<your account pass>>
  offsetHours: 24
```
# testing

1. Run "file-storage" application
2. Run "frontend"
