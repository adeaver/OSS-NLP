# This Python Script goes through the data and pulls out bigrams of positive and negative movie reviews
# The bigrams that it pulls out are anything tagged as:
# Adjective Noun
# Adverb Adjective
# Adjective Adjective
# Noun Adjective
# Adverb Verb

# This pattern was adopted from Pimwadee Chaovalit and Lina Zhou

from pattern.en import parse
import os

def get_unigrams(word_list):
    unigrams_list = []
    add_string = ""

    for word in word_list:
        if(word == "not" or word == "no"):
            add_string += word + "+"
        else:
            add_string += word
            if(add_string != ""):
                unigrams_list.append(add_string)
            add_string = ""

    return unigrams_list

def combine_lists(list1, list2):
    for element in list2:
        if element not in list1:
            list1.append(element)

    return list1

def get_sentiment_unigrams(paths):
    unigrams_list = []

    for path in paths:
        f = open(path, "r+")
        review_text = f.read()
        f.close()

        sentences = review_text.split(".")

        for sentence in sentences:
            processed_sentence = sentence.lower().split(" ")
            sentence_unigrams = get_unigrams(processed_sentence)

            unigrams_list = combine_lists(unigrams_list, sentence_unigrams)

    return unigrams_list

positive_paths = ['./txt_sentoken/pos/' + path for path in os.listdir('./txt_sentoken/pos/')]
negative_paths = ['./txt_sentoken/neg/' + path for path in os.listdir('./txt_sentoken/neg/')]

print "Getting Positive Unigrams"
positive_unigrams = get_sentiment_unigrams(positive_paths)

print "Retrieved " + str(len(positive_unigrams)) + " positive unigrams"

print "Getting Negative Unigrams"
negative_unigrams = get_sentiment_unigrams(negative_paths)

print "Retrieved " + str(len(negative_unigrams)) + " negative unigrams"

pos_out = open('./unigrams/positive_unigrams.txt', 'w')
neg_out = open('./unigrams/negative_unigrams.txt', 'w')

print "Writing Positive Unigrams"
pos_out.write('\n'.join([str(unigram) for unigram in positive_unigrams]))

print "Writing Negative Unigrams"
neg_out.write('\n'.join([str(unigram) for unigram in negative_unigrams]))

neg_out.close()
pos_out.close()
