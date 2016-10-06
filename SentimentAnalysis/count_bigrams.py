# This file counts the bigrams from the positive and negative bigrams
# it then pickles it

import cPickle as pickle
import string, os

def read_generated_bigrams():
    bigram_list = []

    positive_bigrams = open("./bigrams/positive_bigrams.txt")
    pos_bigrams = positive_bigrams.read().split("\n")

    negative_bigrams = open("./bigrams/negative_bigrams.txt")
    neg_bigrams = negative_bigrams.read().split("\n")

    for bigram in pos_bigrams:
        if bigram not in bigram_list:
            bigram_list.append(bigram)

    for bigram in neg_bigrams:
        if bigram not in bigram_list:
            bigram_list.append(bigram)

    positive_bigrams.close()
    negative_bigrams.close()

    return bigram_list

def get_position_dict(bigram_list):
    return {bigram_list[i]:i for i in range(len(bigram_list))}

def clean_text(text):
    return text.translate(None, string.punctuation).lower().split()

def count_bigrams(text_list, freq_array, pos_dict):
    for index in range(len(text_list)-1):
        bigram = text_list[index] + " " + text_list[index+1]

        pos = pos_dict.get(bigram, -1)

        if(pos >= 0 and freq_array[pos] == 0):
            freq_array[pos] = 1

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

def pickle_data(position_dict, data_list):
    output_file = open("features/features.dat", "wb")

    output = {"features":position_dict, "data":data_list}
    pickle.dump(output, output_file)

    output_file.close()

positive_paths = os.listdir("sets/train/pos/")
negative_paths = os.listdir("sets/train/neg/")

print "Reading bigrams"
position_dict = get_position_dict(read_generated_bigrams())
data_list = []

print "Processing Positives"
data_list = process_paths(positive_paths, position_dict, data_list, True, "./sets/train/pos/")

print "Processing Negatives"
data_list = process_paths(negative_paths, position_dict, data_list, False, "./sets/train/neg/")

print "Pickling Data"
pickle_data(position_dict, data_list)
