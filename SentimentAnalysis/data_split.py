# Splits the data up into cross validation and test sets

import os
from shutil import copy2

def copy_train_and_cv(paths, from_dir, train, cv, max_train):
    for index in range(len(paths)):
        if(index == max_train):
            print "Switching to Cross-Validation"
        if(index < max_train):
            copy2(from_dir + paths[index], train + paths[index])
        else:
            copy2(from_dir + paths[index], cv + paths[index])

positive_paths = os.listdir('./txt_sentoken/pos/')
negative_paths = os.listdir('./txt_sentoken/neg/')

pos_from = './txt_sentoken/pos/'
neg_from = './txt_sentoken/neg/'

pos_train_to = './sets/train/pos/'
neg_train_to = './sets/train/neg/'

pos_cv_to = './sets/cv/pos/'
neg_cv_to = './sets/cv/neg/'

max_train_pos = int(len(positive_paths) * .7)
max_train_neg = int(len(negative_paths) * .7)

print "Positives"
copy_train_and_cv(positive_paths, pos_from, pos_train_to, pos_cv_to, max_train_pos)

print "Negatives"
copy_train_and_cv(negative_paths, neg_from, neg_train_to, neg_cv_to, max_train_neg)