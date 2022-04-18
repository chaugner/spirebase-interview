# Java Interview Project

## Current Data Processing Engine

Assume you have a service mesh of many microservices responsible for data exchange between third party systems and spirebase. Part of the process is a data consumer application that routinely receives json data from various external sources. As data is received, a sequential counter is added to the json data. For example consider the following incoming data work load:

```
{
    "keys" : "values"
}
```

The receiver will add a uniuqe sequentail id such as:
```
{
    "id" : 123123,
    data : {
        "keys" : "values"
    }
}
```

This helps keeping track of order that those messages are received in. After receiving and tagging the data it is placed on a shared mount on the file system that is accessible by other applications. The files placed onto the file system will then be picked up by another data processor that simply takes the file from the file system, processes the data and deletes the file after completion.

## New File Monitor Application (The Archiver)

For better monitoring of files being processed, we have built a small file monitor application that simply looks at the files on the mounting location and stores the file name in the database. We will use this to create reports to give us information about files being processed.

To ensure downstream systems can use this information, we will be adding a new attribute on the json data to indicate that this file has been archived.
```
{
    "id" : 123123,
    data : {
        "keys" : "values"
    },
    "archived" : "date-time-value"
}
```

Once released, applications that process files in those mounts may decide to only process them if they have been archived already in the future.

## Docker Compose

We have added a docker compose with postgres to make it easier to run the spring boot application locally.