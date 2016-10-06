# -*- coding: utf-8 -*-

# This file takes in the cleaned data and performs logistic regression
# Then in predicts based on unseen examples

from sklearn.linear_model import LogisticRegression
from sklearn import svm
import cPickle as pickle
import os, string

def clean_text(text):
    return text.translate(None, string.punctuation).lower().split()

def count_bigrams(text_list, freq_array, pos_dict):
    for index in range(len(text_list)-1):
        bigram = text_list[index] + " " + text_list[index+1]

        pos = pos_dict.get(bigram, -1)

        if(pos >= 0 and freq_array[pos] == 0):
            freq_array[pos] = 1

    return freq_array

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

input_file = open("features/clean_features.dat", "rb")

data = pickle.load(input_file)

input_file.close()

features = data["features"]
training_examples = data["data"]
training_classes = data["classes"]

model = LogisticRegression()
model.fit(training_examples, training_classes)

svm_model = svm.SVC()
svm_model.fit(training_examples, training_classes)

positive_paths = os.listdir("./sets/cv/pos/")
negative_paths = os.listdir("./sets/cv/neg/")

data_list = []

data_list = process_paths(positive_paths, features, data_list, True, "./sets/cv/pos/")
data_list = process_paths(negative_paths, features, data_list, False, "./sets/cv/neg/")

X_test = [data_list[i]["features"] for i in range(len(data_list))]
Y_test = [data_list[i]["positive"] for i in range(len(data_list))]

neg_review = '''
“The worst of the worst.” 
That is the phrase chosen by Amanda Waller (Viola Davis), in “Suicide Squad,” to describe the ragtag group of ne'er-do-wells that she has put together in the national interest. 
Waller is a senior government official, as we can tell from the sturdy folk in uniform with whom she confers, and also from the file, brightly labelled “Top Secret,” that we glimpse in her briefcase. 
At one point, somebody refers to her as “God.” That could explain a lot. Waller’s reasoning, if I follow it correctly, is that the next terrorist could be a superhero. 
(It’s an interesting theory, given the damage and distress that is currently being caused, in Europe, America, and elsewhere, by individuals who, far from being preternaturally gifted, are easily gulled, unsound of mind, and short on social skills. 
But this is a movie based on DC comics, and is therefore unlikely to brush more than glancingly against the world we know.) 
If so, what can we possibly use to combat so terrible a threat? “Meta-humans,” apparently, all of them guilty of multiple sins, and most of them languishing in jail. 
The question is: Can these bad guys be persuaded to do some good? Will they be friends and play nicely? 
And, above all, what percentage of them will pass up the chance for some really top-flight, weapons-grade overacting? First up is Deadshot (Will Smith). You’ll never guess how he earned his nickname. 
He murders for money, although, at bottom, to judge by the scenes with his eleven-year-old daughter, he is capable of love. 
Next up is Harley Quinn (Margot Robbie), a psychiatrist turned psychopath—that  old story. She now wears her hair in bunches and giggles at the prospect of destruction. 
Her paramour is the Joker (Jared Leto), who has braces on his teeth and hair like freshly mowed grass. 
Also in the offing is Killer Croc (Adewale Akinnuoye-Agbaje), whose dermatological issues are so clearly indebted to those of Ben Grimm, in “Fantastic Four,” that I can hear the distant rumble of a lawsuit. 
Then comes Diablo (Jay Hernandez), who is a touch more flamboyant than he can handle, plus an Australian called Captain Boomerang (Jai Courtney)—because, you know, that’s the only weapon that Australians ever use. 
On the same principle, Katana (Karen Fukuhara), being Asian, wields a curved ceremonial sword. (What a pitiful thing the DC map of the world must be, with each country identified by nothing more than its legendary tool of aggression.) Katana is one of the extra recruits, tossed in without much ado as the action begins to stir. 
The other is Slipknot (Adam Beach), a cheerful soul who is said to be very good at climbing. He doesn’t last long. The writer and director is David Ayer, whose previous film was “Fury,” a Second World War drama in which Brad Pitt held off what appeared to be the entire German Army with a single tank. That was a model of clear-thinking sobriety compared with “Suicide Squad.” To say that the movie loses the plot would not be strictly accurate, for that would imply that there was a plot to lose, and that Ayer, in a forgetful moment, left it in the glove compartment of his car on the way to the studio. My suspicion is that “Suicide Squad” was always more of a package, a conceptual wheeze, or a half-developed pitch than a plausible story. True, there is some fathomless nonsense about Dr. June Moone (Cara Delevingne), an archeologist by profession, but no narrative could hope to contain her illimitable powers. Transformed into an enchantress after an unfortunate jungle experience, she can a) vanish in a puff of black smoke, b) summon her big brother with a stream of haunting gobbledygook, c) boogie amid a blinding light show, clad only in a mystical bikini, and, most impressive of all, d) still find time to go out with Rick Flag (Joel Kinnaman), whose very surname reminds him of his duty. He is, we learn, “the finest Special Forces officer this nation has ever produced.”
Note the superlative—the grammatical form of choice for comic-book adjectives. Nothing in this movie is ever middling, or allowed to muddle along. Nobody has an O.K. day. Instead, Deadshot is “the most-wanted hit man in the world.” Harley Quinn and the Joker are “the king and queen of Gotham City.” This perpetual overreach, desperate to outdo anything that might smack of regularity, has the genuine tang of adolescence; it is as though all the characters, even the ones not adorned with tattoos, are straining to shock their parents or to drive them nuts. When an actress as distinguished as Davis has to pick up a gun and waste a few co-workers, on the ground that they lacked security clearance, you realize that the film’s addiction to extremity has infected not merely its phrasing but also its range of available gestures. Needless to say, armaments are vital to that cause. One overhead shot pulls back to show the Joker encircled with a halo of knives and other instruments of pain, in mock sanctification of his sadistic calling. Later, Steven Price’s music soars to triumphal heights as Deadshot, tall and proud on the roof of a car, dispatches one enemy assailant after another, while U.S. soldiers, bereft of his criminal record and his unearthly talent, lower their firearms and gaze in undisguised awe at the man’s hostility. Some viewers, in turn, will stare in equal wonder and ask themselves, What are the chances for gun control, honestly, if this is what Hollywood—supposedly a fortress of liberal attitudes—prefers to hold aloft, these days, by way of a heroic stand?
Here and there in “Suicide Squad,” nonetheless, Smith does find the opportunity to remind us of why he became a star. One brief conversation between Deadshot and Waller, in which&nbsp;he lists the conditions on which he will agree to join her nefarious club, has some of the snap, crackle, and pop that the young Smith brought to his dialogue, and that he seems to have mislaid of late. Give the man a movie to himself, and lines with some crunch to them; he deserves better than to be corralled with this crew. A couple of them, to be fair, emerge from the wreckage intact; Robbie, for instance, may be given little more than one note to strike, chirruping with glee as the mayhem thickens, but she hits it sweetly enough, and Davis remains unbowed. When it comes to Leto and Delevingne, on the other hand, all I can say is: ladies and gentlemen, place your bets, and try to guess which performance, when the movies of 2016 are tallied and assessed, will be judged the more embarrassing. I would plump for Leto, who lets not a syllable go unmangled, and whose attempt at pure evil is roughly as frightening as “Goodnight Moon,” but I could be wrong.
Then there’s the name of the film. I’m not convinced of the wisdom, let alone the entertainment value, of putting “suicide” in the title of a major release; as for the squadding, it barely exists. There is a roster of movies, reaching back through “The Wild Bunch” (1969) to “Seven Samurai” (1954) and beyond, in which the binding and the bonding of the fighters, however fraught, acted as a humane counterpoise to the violence that they were destined, or designed, to mete out. Where is that fellowship to be found, in Ayer’s film? Only in flickers, as a token of grudging restraint; one character, after a pause, refrains from killing Flag, but that’s scarcely the same as laying down your life for a pal. By the end, with a planetary crisis averted, and the ground grimly laid for a sequel, you feel both exhausted and hollow, as if a whole lot of nothing had just thundered by. Almost eighty years ago, Warner Bros. gave us “Angels with Dirty Faces,” starring Cagney and Bogart—proper tough guys—and the Dead End Kids. Now, courtesy of the same studio, we get “Suicide Squad,” and its turbulent travesty of menace. The worst of the worst? Maybe not. But it’s a dead end for kids.
'''

parsed = clean_text(neg_review)
neg_freq_array = count_unigrams(parsed, [0]*len(features), features)

print "Logistic Regression Score: " + str(model.score(X_test, Y_test))
print "Logistic Regression Prediction: " + str(model.predict([neg_freq_array]))

print "SVM Score: " + str(svm_model.score(X_test, Y_test))
print "SVM Prediction: " + str(svm_model.predict([neg_freq_array]))
