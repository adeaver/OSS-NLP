# This file takes the bigram data and removes all of the features below a certain threshold

from sklearn.feature_selection import VarianceThreshold
import cPickle as pickle

def load_data():
    feature_data = open("features/features.dat", "rb")

    data = pickle.load(feature_data)
    features = data["features"]
    data_list = data["data"]

    feature_data.close()

    X = [data_list[index]["features"] for index in range(len(data_list))]
    Y = [data_list[index]["positive"] for index in range(len(data_list))]

    return X, Y, features


def remove_low_variance_features(X, threshold):
    vt = VarianceThreshold()
    vt.fit(X)

    return [index for index in range(len(vt.variances_)) if vt.variances_[index] > VARIANCE_THRESHOLD]

def remove_below_average_occurences(X, threshold):
    avg_x = [0] * len(X[0])

    for feats in X:
        for i in range(len(feats)):
            if(feats[i] != 0):
                avg_x[i] += 1

    return [index for index in range(len(avg_x)) if avg_x[index] >= threshold]

def show_num_occurences(X, features):
    avg_x = [0] * len(X[0])
    sorted_features = sorted(features.items(), key=lambda x: x[1])

    for feats in X:
        for i in range(len(feats)):
            if(feats[i] != 0):
                avg_x[i] += 1

    for i in range(len(avg_x)):
        print sorted_features[i][0] + ": " + str(avg_x[i])

def update_features(features, X, indexes_to_use, do_print):
    print "USING: " + str(len(indexes_to_use)) + " FEATURES"

    sorted_features = sorted(features.items(), key=lambda x: x[1])
    new_features = dict()

    for i in range(len(indexes_to_use)):
        feature_index = indexes_to_use[i]
        new_features[sorted_features[feature_index][0]] = i
        if do_print:
            print sorted_features[feature_index][0]

    new_x = []

    for x in X:
        temp = [x[index] for index in indexes_to_use]
        new_x.append(temp)

    return new_features, new_x



VARIANCE_THRESHOLD = 0.01

X, Y, features = load_data()
indices = remove_low_variance_features(X, VARIANCE_THRESHOLD)
features, X = update_features(features, X, indices, False)

show_num_occurences(X, features)

clean_feature_data = open("features/clean_features.dat", "wb")

output = {"features":features, "data":X, "classes":Y}
pickle.dump(output, clean_feature_data)

clean_feature_data.close()