import re
import numpy as np

def parse_text(filename='words.txt'):
    words_dict = {}
    with open(filename) as fl:
        cnt = 0
        for word in fl:
            if not(word.strip('\n') in words_dict):
                words_dict[word.strip('\n')] = cnt
                cnt += 1
    print("Words are parsed")
    return words_dict

def text_to_vector(text, words_dict):
    text_list = re.split('[^a-z]', text.lower())
    text_list = [word for word in text_list if word != ""]
    n = len(words_dict)
    x = np.zeros(n)
    for word in text_list:
        index = words_dict.get(word, -1)
        if index != -1:
            x[index] += 1
            
    return x


