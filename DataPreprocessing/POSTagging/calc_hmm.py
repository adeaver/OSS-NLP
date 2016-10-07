# This file counts the bigrams and trigrams for parts of speech
# as well as the emission probability for each word

from _collections import *
import mysql.connector

def process_word(word):
    return tuple(word.split("_"))

def count_word(word, pos, word_dict):
    count_dict = word_dict.get(word, dict())
    count = count_dict.get(pos, 0)+1
    count_dict[pos] = count
    word_dict[word] = count_dict
    return word_dict

def count_pos(gram_list, gram_counts):
    trigram = ""
    bigram = ""

    list_length = len(gram_list)
    
    if(list_length == 3):
        trigram = ' '.join(gram_list)
        trigram_count = gram_counts.get(trigram, 0)
        gram_counts[trigram] = trigram_count + 1

    if(list_length >= 2):
        bigram = ' '.join([gram_list[list_length-2], gram_list[list_length-1]])
        bigram_count = gram_counts.get(bigram, 0)
        gram_counts[bigram] = bigram_count + 1

    return gram_counts

def database_dump(connection, word_counts, gram_counts, pos_tags, insertTags=False):
    cursor = connection.cursor()
    
    insert_bigrams = "INSERT INTO transitions(bigram, count) VALUES(%s, %s) ON DUPLICATE KEY UPDATE count=count+%s"
    insert_trigrams = "INSERT INTO trigrams(trigram, count) VALUES(%s, %s) ON DUPLICATE KEY UPDATE count=count+%s"
    insert_emissions = "INSERT INTO emissions(word, pos, count) VALUES(%s, %s, %s) ON DUPLICATE KEY UPDATE count=count+%s"
    insert_tags = "INSERT INTO pos_tags(pos) VALUES(%s)"

    for word in gram_counts.keys():
        n = len(word.split())
        if(n == 2):
            bigram_c = str(gram_counts[word])
            data = [word, bigram_c, bigram_c]
            cursor.execute(insert_bigrams, data)
        elif(n == 3):
            trigram_c = str(gram_counts[word])
            data = [word, trigram_c, trigram_c]
            cursor.execute(insert_trigrams, data)

    for word in word_counts.keys():
        pos_counts = word_counts[word]
        for pos in pos_counts.keys():
            data = [word, pos, pos_counts[pos], pos_counts[pos]]
            cursor.execute(insert_emissions, data)

    if insertTags:
        for tag in pos_tags:
            data = [tag]
            cursor.execute(insert_tags, data)
 
    cursor.close()

   
conn = mysql.connector.connect(user='pos_tagger', password='hmms', database='pos_tagging')
 
f = open("browntag_nolines.txt", "r+")
tagged_lines = f.readlines()

word_counts = dict()
gram_counts = dict()
pos_tags = []

for index in range(len(tagged_lines)):
    grams = deque()
   
    grams.append("<s>") 
    tagged_words = tagged_lines[index].split()

    if index%50 == 0:
        database_dump(conn, word_counts, gram_counts, pos_tags, False)
        gram_counts = dict()
        word_counts = dict()

    for word in tagged_words:
        processed = process_word(word)
        
        if(len(grams) == 3):
            grams.popleft()

        if(processed[1] not in pos_tags):
            pos_tags.append(processed[1])
        
        grams.append(processed[1])
        
        word_counts = count_word(processed[0], processed[1], word_counts)
        gram_counts = count_pos(grams, gram_counts)


database_dump(conn, word_counts, gram_counts, pos_tags, True)

conn.commit()
conn.close()
