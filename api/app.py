from flask import Flask, render_template, request, session, send_file
import json
import example as ex
import base64

app = Flask(__name__)


@app.route('/get_pictures/', methods = ['POST'])
def get_pictures():
    text = request.get_json()
    base64_array = []
    array = ex.mock(text.get("text"))
    for pict in array:
        with open('./memes/'+pict+'.jpg', "rb") as image_file:
            encoded = base64.b64encode(image_file.read())
            base64_array.append(encoded.decode('utf-8'))
    json_data = json.dumps(base64_array)
    return json_data


if __name__ == '__main__':
    app.run()
