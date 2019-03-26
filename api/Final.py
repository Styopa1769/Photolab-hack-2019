
# coding: utf-8

# In[ ]:


# standart modules
import pickle
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt

# ML
from sklearn.linear_model import LogisticRegression
import keras
from keras.preprocessing.sequence import pad_sequences
from keras.models import load_model
from sklearn.metrics import classification_report, confusion_matrix
import itertools, pickle
MAX_SEQUENCE_LENGTH = 30 # max length of text (words) including padding

emotions = ["neutral", "happy", "sad", "hate", "anger"]
with open('txt_model/tokenizer.pickle', 'rb') as handle:
        tokenizer = pickle.load(handle)

# text processing
import words
from scipy.spatial.distance import cosine

def load_text_emotion_model():
    json_file = open('txt_model/model_200_epochs.json', 'r')

    loaded_model_json = json_file.read()
    json_file.close()
    loaded_model = keras.models.model_from_json(loaded_model_json)

    # load weights into new model
    loaded_model.load_weights("txt_model/model_200_epochs.h5")
    print("Text emotion model is loaded")
    
    return loaded_model

def get_emotion_text(text, model):
    sequences_test = tokenizer.texts_to_sequences([text])
    data_int_t = pad_sequences(sequences_test, padding='pre', maxlen=(MAX_SEQUENCE_LENGTH-5))
    data_test = pad_sequences(data_int_t, padding='post', maxlen=(MAX_SEQUENCE_LENGTH))
    proba = model.predict(data_test)
    return proba

def get_emotion_euristic(df, j, text, text_emotion_model):
    emotion_proba = get_emotion_text(text, text_emotion_model)
    m = df.shape[0]
    emotion_index = df.loc[j]['emotion']
    emotion_euristic = emotion_proba[0, int(emotion_index)]
    return emotion_euristic

def get_sense_euristic(df, j, text, words_dict):
    m = df.shape[0]
    k = len(words_dict)
    senses_matrix = np.zeros((m, k))
    text_vector = words.text_to_vector(text, words_dict)
    mem_vector = words.text_to_vector(df.loc[j]['data'], words_dict)
    if (np.linalg.norm(text_vector) != 0) and (np.linalg.norm(mem_vector) != 0):
        sense_euristic = 1 - cosine(mem_vector, text_vector)
    else:
        sense_euristic = 0.0
    return sense_euristic

def load_memes_model():
    text_emotion_model = load_text_emotion_model()
    words_dict = words.parse_text()
    m = 200
    regressions = [None] * m
    for j in range(m):
        filename = 'models/' + str(j) + '_finalized_model.sav'
        regressions[j] = pickle.load(open(filename, 'rb')) 
    return regressions, words_dict, text_emotion_model

def predict_memes(text, text_emotion_model, words_dict, regressions):
    m = 200
    memes_df = pd.read_csv("memes_df.csv")
    proba_array = np.zeros(m)
    for j in range(m):
        emotion_euristics = get_emotion_euristic(memes_df, j, text, text_emotion_model)
        sense_euristics = get_sense_euristic(memes_df, j, text, words_dict)
        euristics = np.array([emotion_euristics, sense_euristics])
        proba = regressions[j].predict_proba(euristics.T.reshape(1, 2))[0, 1]
        proba_array[j] = proba
    
    indexes = np.argsort(-proba_array)
    return memes_df.loc[indexes[0: 4]]["name"].values

