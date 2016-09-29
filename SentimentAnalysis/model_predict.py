# This file takes in the cleaned data and performs logistic regression
# Then in predicts based on unseen examples

from sklearn.linear_model import LogisticRegression
import cPickle as pickle
import os, string

def clean_text(text):
    return text.translate(None, string.punctuation).lower().split()

def count_bigrams(text_list, freq_array, pos_dict):
    for index in range(len(text_list)-1):
        bigram = text_list[index] + " " + text_list[index+1]

        pos = pos_dict.get(bigram, -1)

        if(pos >= 0):
            freq_array[pos] += 1

    return freq_array

def process_paths(paths, position_dict, data_list, positives, path_prefix):
    positivity = 1 if positives else 0

    for path in paths:
        freq_array = [0] * len(position_dict)

        text_file = open(path_prefix + path, "r+")
        text = text_file.read()
        text_file.close()

        for_processing = clean_text(text)
        freq_array = count_bigrams(for_processing, freq_array, position_dict)

        data = {"features":freq_array, "positive":positivity}
        data_list.append(data)

    return data_list

input_file = open("features/clean_features.dat", "rb")

data = pickle.load(input_file)

input_file.close()

features = data["features"]
training_examples = data["data"]
training_classes = data["classes"]

model = LogisticRegression()
model.fit(training_examples, training_classes)

positive_paths = os.listdir("./sets/cv/pos/")
negative_paths = os.listdir("./sets/cv/neg/")

data_list = []

data_list = process_paths(positive_paths, features, data_list, True, "./sets/cv/pos/")
data_list = process_paths(negative_paths, features, data_list, False, "./sets/cv/neg/")

X_test = [data_list[i]["features"] for i in range(len(data_list))]
Y_test = [data_list[i]["positive"] for i in range(len(data_list))]

print model.score(X_test, Y_test) 