# Statistical Grammar Correction 

This README will provide information for how the code base works. Be sure to read the javadocs for GrammarCorrection.

## Preprocessing

The first step is tokenizing the sentence. At first, this means that the sentence will be split up, each word as its own entity, and turned into a series of Token objects.

#### Token

The Token class contains information about how to act on a group of words. Each Token has a ```headWord``` which dictates how the token is treated. A Token may be a single word or a collection of words. For instance, an entire Phrasal verb may be treated as a single token. An example of this would be the phrase "Share the website with me" could be treated as an entire Token with the ```headWord``` being "share."

## Anatomy of a Finite State Module

All of the modules that use finite state machines to determine potential errors (which are in the form of transductions) require the following class definitions. As of right now, all of the modules follow this model; however, in the future, there may be models that use other parsing techniques.

#### CorrectionService

CorrectionServices are classes that inherit from the CorrectionService super class (com.ardeaver.grammar.correction). These classes are essential for identifying possible errors and correcting them. In order to define a CorrectionService, you must implement the ```transformTransductions()``` method. This method will define how each correction service converts the transductions found by running the FiniteStateMachine into corrections that the CorrectionService replaces.

Additionally, you will need to define and provide a FiniteStateMachine.

#### Finite State Machine

These also require a class that inherits from the FiniteStateMachine super class (com.ardeaver.grammar.fsa.FiniteStateMachine). Finite State Machine classes do <strong>not</strong> require you to override any methods. Instead all you need to do is provide the following classes in the module.

* TransitionTable
* Transduction
* TransductionFactory
* InputConverter

The definitions of these classes will determine what the FiniteStateMachine determines is an error.

#### TransitionTable

This exists at the heart of each FiniteStateMachine. Transition Tables specify the next state given the current state and the input. In order to create a transition table, you must inherit from the TransitionTable super class (com.ardeaver.grammar.fsa.TransitionTable) and implement its ```getAcceptingStates()``` method. This method, as it would appear obvious, returns a list of the accepting states of the FiniteStateMachine. You must also provide a 3-Dimensional Array. Each row represents the current state, each column represents the input, and each entry is a list of all of the possible states given that input and state. A ```null``` entry represents a transition that is not possible.

#### Transduction

Transductions represent potential errors. They are the output of FiniteStateMachines. In order to implement a Transduction, you must implement all of its methods.

#### TransductionFactory

TransductionFactorys are used by FiniteStateMachines to create new instances of Transductions. In order to implement one, you must only override the ```getTransduction()``` method. Usually this simply involves returning a new instance of a Transduction.

#### InputConverter

InputConverters are used by FiniteStateMachines to create a numeric representation of a given input. Usually input corresponds to a part of speech and the output is a number representing the group that the input is in. A group might be represented by a number of different parts of speech. For example: auxiliary verbs like be, will, have, etc.

## Prepositional Phrases Module

The prepositional phrases module exists to correct issues with incorrect use of prepositions. For instance, certain verbs and nouns in prepositional phrases require certain prepositions to connect them. For instance, "The President of the United States" or "Accused of being." This module is further split into two submodules, one fixing issues with nouns and one fixing issues with verbs. This is done to not interfere with the work of phrasal verbs later.

Both submodules work using the same CorrectionService and the same Transition Table. The difference is the type of prepositional phrases that are corrected. The diagram that they both use to detect errors is as follows.

![Prepositional Phrase Finite State Diagram] (https://github.com/adeaver/Alea-Sandbox/blob/master/documentation/Alea_PrepositionalFST.png)

## Subject Verb Agreement Module

Currently, the Subject Verb Agreement Module operates similarly to the other modules. There is a Correction Service, Transduction, Transition Table, etc. The Subject Verb Agreement Module operates on the most complex finite state machine. This is designed to find appropriate noun phrases and verbs (or combinations thereof) and correct them all. Below are the parts of the finite state machine.

![Subject Verb Agreement Finite State Diagram Part 1] (https://github.com/adeaver/Alea-Sandbox/blob/master/documentation/SubjectVerbAgreementNP.png)
![Subject Verb Agreement Finite State Diagram Part 2] (https://github.com/adeaver/Alea-Sandbox/blob/master/documentation/SubjectVerbAgreementTriangle.png)
![Subject Verb Agreement Finite State Diagram Part 3] (https://github.com/adeaver/Alea-Sandbox/blob/master/documentation/SubjectVerbAgreementBox.png)
![Subject Verb Agreement Finite State Diagram Part 4] (https://github.com/adeaver/Alea-Sandbox/blob/master/documentation/SubjectVerbAgreementStar.png)
