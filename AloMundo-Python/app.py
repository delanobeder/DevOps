from flask import Flask
from datetime import datetime

app = Flask(__name__)

@app.route('/')
def hello_geek():
    date = datetime.now().strftime('%A, %d. %B %Y %I:%M:%S %p')
    return '<p>Hello World!</p><p>'+ date +'</p>'


if __name__ == "__main__":
    app.run(debug=True)