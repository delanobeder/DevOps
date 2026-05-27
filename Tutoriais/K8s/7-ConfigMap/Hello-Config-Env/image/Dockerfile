FROM golang:1.17-alpine

WORKDIR /src/

COPY main.go go.* /src/

RUN CGO_ENABLED=0 go build -o /bin/demo

ENTRYPOINT ["/bin/demo"]
