# Pharmacy Project
![enter image description here](https://travis-ci.com/milospp/mrs-isa.svg?token=QFrKDHpi7sZxEk17Xky9&branch=main)
MRS-ISA Project


# Running a project
TODO
# Demo
https://pharmacy9.herokuapp.com/
[backend API url](https://pharmacy9.herokuapp.com/)

> Project is deployed to free Heroku server, and because of that performances are limited.
> After 30min of inactivity server will be automatically shut downed and it will take some time to wake up on the next request

## Team

| 1 |2  | 3 | 4 |
|--|--|--|--|
| Miloš Popović | Milica Đumić | Isidora Stanić | Mladen Vasić |
| SW-24/2018 | SW-27/2018 | SW-16/2018 | SW-28/2018 |




## Deployment
Looks like it's not working here :(

```mermaid
graph LR
A[Local] -- PUSH --> C{GIT}
B[Local] --> C


C --> D{Travis CI}
D --> E{Heroku frontend}
D --> F{Heroku backend}
D --> G{SonarCloud}

