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

def get_bigrams(tagged_list):
    bigrams_list = []

    for index in range(len(tagged_list)-1):
        first_tag_phrase = process_tag(tagged_list[index])
        second_tag_phrase = process_tag(tagged_list[index+1])

        if should_add_bigram(first_tag_phrase, second_tag_phrase):
            bigram = tuple([first_tag_phrase[0], second_tag_phrase[0]])

            if bigram not in bigrams_list:
                bigrams_list.append(bigram)

    return bigrams_list

def process_tag(tag):
    split_tag = tag.split("/")
    return tuple([split_tag[0], split_tag[1]])

def should_add_bigram(tag1, tag2):
    if 'JJ' in tag1[1] and 'NN' in tag2[1]:
        return True
    if 'RB' in tag1[1] and 'JJ' in tag2[1]:
        return True
    elif 'JJ' in tag1[1] and 'JJ' in tag2[1]:
        return True
    elif 'NN' in tag1[1] and 'JJ' in tag2[1]:
        return True
    elif 'RB' in tag1[1] and 'VB' in tag2[1]:
        return True
    return False

def combine_lists(list1, list2):
    for element in list2:
        if element not in list1:
            list1.append(element)

    return list1

def get_sentiment_bigrams(paths):
    bigrams_list = []

    for path in paths:
        f = open(path, "r+")
        review_text = f.read()
        f.close()

        sentences = review_text.split(".")

        for sentence in sentences:
            tagged_sentence = parse(sentence.lower()).split(" ")
            sentence_bigrams = get_bigrams(tagged_sentence)

            bigrams_list = combine_lists(bigrams_list, sentence_bigrams)

    return bigrams_list

positive_paths = ['./txt_sentoken/pos/' + path for path in os.listdir('./txt_sentoken/pos/')]
negative_paths = ['./txt_sentoken/neg/' + path for path in os.listdir('./txt_sentoken/neg/')]

print "Getting Positive Bigrams"
positive_bigrams = get_sentiment_bigrams(positive_paths)

print "Retrieved " + str(len(positive_bigrams)) + " positive bigrams"

print "Getting Negative Bigrams"
negative_bigrams = get_sentiment_bigrams(negative_paths)

print "Retrieved " + str(len(negative_bigrams)) + " negative bigrams"

pos_out = open('./bigrams/positive_bigrams.txt', 'w')
neg_out = open('./bigrams/negative_bigrams.txt', 'w')

print "Writing Positive Bigrams"
pos_out.write('\n'.join([str(bigram[0]) + " " + str(bigram[1]) for bigram in positive_bigrams]))

print "Writing Negative Bigrams"
neg_out.write('\n'.join([str(bigram[0]) + " " + str(bigram[1]) for bigram in negative_bigrams]))

neg_out.close()
pos_out.close()