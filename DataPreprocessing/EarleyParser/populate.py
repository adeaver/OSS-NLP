import mysql.connector
import os, re
from _collections import *

BASE_DIRECTORY = "../Corpora/naist-ntt-ted-treebank-v1/en-mrg/"

files = [BASE_DIRECTORY + f for f in os.listdir(BASE_DIRECTORY)]

def process_all_files(files, connection):
    rules = []

    for f in files:
        of = open(f, "rb")
        lines = of.readlines()
        of.close()

        rules = process_all_rules(lines)
        add_rules(connection, rules)

def process_line(nline):
    line = ""

    while(line != nline):
        line = nline
        nline = re.sub(r"(\))(\))", r"\1 \2", line)

    return line

def add_rules(connection, rules):
    cursor = connection.cursor()

    query = "INSERT INTO parses VALUES(%s, %s, %s) ON DUPLICATE KEY UPDATE count=count+%s"

    for rule in rules:
        data = [rule[0], rule[1], "1", "1"]
        cursor.execute(query, data)

    cursor.close()

def process_all_rules(lines):
    all_rules = []

    for line in lines:
        p = process_line(line)
        all_rules += get_rules(p)

    return all_rules

def get_rules(line):
    rules = []
    first_level = get_level(process_line(line))

    if(len(first_level) > 0):
        second_level = get_level(first_level[0][1])

        rule, to_process = get_grammar_rule(second_level[0][0], second_level[0][1])
        rules.append(rule)

        while(len(to_process) > 0):
            rule, add = get_grammar_rule(to_process[0][0], to_process[0][1])

            if(len(add) != 0):
                rules.append(rule)

            to_process = to_process[1:len(to_process)] + add

    return rules

def get_grammar_rule(current, line):
    levels = get_level(line)
    to_process = []
    rule = ""

    for level in levels:
        if(level[0] != "."):
            rule += level[0] + " "
            to_process.append((level[0], level[1]))

    return (current, rule.strip()), to_process

def get_level(next_line):
    level = []

    count = 0

    entities = next_line.split(" ")

    for i in range(len(entities)):

        entity = entities[i]

        if("(" in entity):
            if(count == 0):
                level.append((entity.split("(")[1], i))

            count += 1
        elif(")" in entity):
            count -= 1

            if(count == 0):
                last_entity = level[len(level)-1]
                level[len(level)-1] = (last_entity[0], ' '.join(entities[last_entity[1]+1:i]))

    return level

conn = mysql.connector.connect(user='earley', password='parsing', database='earley')
process_all_files(files, conn)

conn.commit()
conn.close()