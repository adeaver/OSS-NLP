# This file counts the unigrams from the positive and negative unigrams
# it then pickles it

import cPickle as pickle
import string, os

def read_generated_unigrams():
    unigram_list = []

    positive_unigrams = open("./unigrams/positive_unigrams.txt")
    pos_unigrams = positive_unigrams.read().split("\n")

    negative_unigrams = open("./unigrams/negative_unigrams.txt")
    neg_unigrams = negative_unigrams.read().split("\n")

    for unigram in pos_unigrams:
        if unigram not in unigram_list:
            unigram_list.append(unigram)

    for unigram in neg_unigrams:
        if unigram not in unigram_list:
            unigram_list.append(unigram)

    positive_unigrams.close()
    negative_unigrams.close()

    return unigram_list

def get_position_dict(unigram_list):
    return {unigram_list[i]:i for i in range(len(unigram_list))}

def clean_text(text):
    return text.translate(None, string.punctuation).lower().split()

def count_unigrams(text_list, freq_array, pos_dict):
    unigram = ""
    for word in text_list:
        if(word == "no" or word == "not"):
            unigram += word + "+"
            continue
        else:
            unigram += word

            pos = pos_dict.get(unigram, -1)

            if(pos >= 0 and freq_array[pos] == 0):
                freq_array[pos] = 1
            
            unigram = ""

    return freq_array

def process_paths(paths, position_dict, data_list, positives, path_prefix):
    positivity = 1 if positives else 0

    for path in paths:
        freq_array = [0] * len(position_dict)

        text_file = open(path_prefix + path, "r+")
        text = text_file.read()
        text_file.close()

        for_processing = clean_text(text)
        freq_array = count_unigrams(for_processing, freq_array, position_dict)

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

print "Reading unigrams"
position_dict = get_position_dict(read_generated_unigrams())
data_list = []

print "Processing Positives"
data_list = process_paths(positive_paths, position_dict, data_list, True, "./sets/train/pos/")

print "Processing Negatives"
data_list = process_paths(negative_paths, position_dict, data_list, False, "./sets/train/neg/")

print "Pickling Data"
pickle_data(position_dict, data_list)
