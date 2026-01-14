FROM golang:1.23

# Set destination for COPY
WORKDIR /app

# Download Go modules
COPY . ./
RUN go mod download
RUN go get github.com/gin-gonic/gin
RUN go get github.com/jinzhu/gorm
RUN go get github.com/jinzhu/gorm/dialects/mysql

# Build
RUN CGO_ENABLED=0 GOOS=linux go build -o /backend

EXPOSE 8081

# Run
CMD ["/backend"]