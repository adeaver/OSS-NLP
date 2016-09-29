# This file takes the bigram data and removes all of the features below a certain threshold

from sklearn.feature_selection import VarianceThreshold
import cPickle as pickle

VARIANCE_THRESHOLD = 0

feature_data = open("features/features.dat", "rb")

data = pickle.load(feature_data)
features = data["features"]
data_list = data["data"]

feature_data.close()

X = [data_list[index]["features"] for index in range(len(data_list))]
Y = [data_list[index]["positive"] for index in range(len(data_list))]

vt = VarianceThreshold()
vt.fit(X)

indexes_to_use = [index for index in range(len(vt.variances_)) if vt.variances_[index] > VARIANCE_THRESHOLD]

print "USING: " + str(len(indexes_to_use)) + " FEATURES"

sorted_features = sorted(features.items(), key=lambda x: x[1])
new_features = dict()

for i in range(len(indexes_to_use)):
    feature_index = indexes_to_use[i]
    new_features[sorted_features[feature_index][0]] = i
    #print sorted_features[feature_index][0]

new_x = []

for x in X:
    temp = [x[index] for index in indexes_to_use]
    new_x.append(temp)

clean_feature_data = open("features/clean_features.dat", "wb")

output = {"features":new_features, "data":new_x, "classes":Y}
pickle.dump(output, clean_feature_data)

clean_feature_data.close()