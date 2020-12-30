export class Container {
  // Container name
  name: string;
  //  BlobContainerItemProperties.leaseState
  leaseState?: string;
  //  BlobContainerItemProperties.leaseStatus
  leaseStatus?: string;
  //  BlobContainerItemProperties.lastModified
  lastModified?: string;
  //  BlobContainerItemProperties.ETag
  etag?: string;
}
