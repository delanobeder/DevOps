FROM golang:1.20

# Set destination for COPY
WORKDIR /app

# Download Go modules
COPY . ./
RUN go mod download

# Build
RUN CGO_ENABLED=0 GOOS=linux go build -o /alomundo-go

EXPOSE 9090

# Run
CMD ["/alomundo-go"]