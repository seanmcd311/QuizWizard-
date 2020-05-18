#include <jni.h>
#include <string>
#include <iostream>
#include <fstream>
#include <string>
#include <vector>



using namespace std;
vector <string> sentences;
int rand_num,rand_num1,rand_num2, rand_num3, find_colon;
int found,found1,found2,found3, found_quote,found_quote1,found_quote2,found_quote3, find_end_of_ques;
string ans,ans1,ans2,ans3, final_ans, sentence,sentence1, sentence2,sentence3, quest, player_choice;
string line, incorrect1,incorrect2,incorrect3;
int choice;
int lives = 2, score = 0;


extern "C" JNIEXPORT jstring JNICALL
Java_com_example_quizwiz_Quizmain_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT void JNICALL
Java_com_example_quizwiz_MainActivity_getsports(
        JNIEnv *env, jobject thiz){

    string line;


    ifstream myfile;
    myfile.open("/Users/seanmcdonald/Downloads/ALLQUIZ/QuizWiz/app/src/main/cpp/sports_questions.txt");

    while(getline(myfile,line)){

        sentences.push_back ( line );

    }
    myfile.close();

}

extern "C" JNIEXPORT void JNICALL
Java_com_example_quizwiz_MainActivity_gethistory(
        JNIEnv *env, jobject thiz) {


    ifstream myfile;
    myfile.open("/Users/seanmcdonald/Downloads/ALLQUIZ/QuizWiz/app/src/main/cpp/history_questions.txt");


        while (getline(myfile, line)) {

            sentences.push_back(line);

        }
        myfile.close();

}

extern "C" JNIEXPORT void JNICALL
Java_com_example_quizwiz_MainActivity_getTV(
        JNIEnv *env, jobject thiz){



    ifstream myfile;
    myfile.open("/Users/seanmcdonald/Downloads/ALLQUIZ/QuizWiz/app/src/main/cpp/tv_questions.txt");

    while(getline(myfile,line)){

        sentences.push_back ( line );

    }
    myfile.close();

}

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_quizwiz_Quizmain_getquestion(
        JNIEnv *env, jobject thiz){


    srand(time(0));

        rand_num = rand() % sentences.size();

        sentence = sentences[rand_num];
        found = sentence.find("\","); //Find instance of ','
        ans = sentence.substr(found + 4, sentence.length()); // Taking subtring from ','+3 to the end

        found_quote = ans.find('\"'); //Finding next "
        final_ans = ans.substr(0, found_quote); //Taking a new substring from initial ans to the found_quote

        find_colon = sentence.find(':');
        string quest_part = sentence.substr(find_colon + 1, sentence.length());
        find_end_of_ques = quest_part.find("\",");
        quest =quest_part.substr(0, find_end_of_ques);

        return env->NewStringUTF(quest.c_str());

}

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_quizwiz_Quizmain_getans(
        JNIEnv *env, jobject thiz){

    return env->NewStringUTF(final_ans.c_str());

}
extern "C" JNIEXPORT void JNICALL
Java_com_example_quizwiz_Quizmain_lifeminus(
        JNIEnv *env, jobject thiz) {
    --lives;
}

extern "C" JNIEXPORT void JNICALL
Java_com_example_quizwiz_Quizmain_scoreplus(
        JNIEnv *env, jobject thiz) {
    ++score;
}
extern "C" JNIEXPORT jstring JNICALL
Java_com_example_quizwiz_Quizmain_score(
       JNIEnv *env, jobject thiz){

    std::string s = std::to_string(score);
    return env->NewStringUTF(s.c_str());

}

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_quizwiz_Quizmain_life(
        JNIEnv *env, jobject thiz){

    std::string l = std::to_string(lives);
    return env->NewStringUTF(l.c_str());

}



extern "C" JNIEXPORT jstring JNICALL
Java_com_example_quizwiz_Quizmain_getincorrect1(
        JNIEnv *env, jobject thiz){


    //srand(time(0));
    //rand_num1 = rand() % sentences.size();
    //while(rand_num1 == rand_num){
    //rand_num1 = rand() % sentences.size();}

    sentence1 = "[\"History: Who was the first man to reach the North Pole\", \"Robert Edwin Peary\"]";
    found1 = sentence1.find("\","); //Find instance of ','
    ans1 = sentence1.substr(found1 + 4, sentence1.length()); // Taking subtring from ','+3 to the end

    found_quote1 = ans1.find('\"'); //Finding next "
    incorrect1 = ans1.substr(0, found_quote1); //Taking a new substring from initial ans to the found_quote


    return env->NewStringUTF(incorrect1.c_str());

}

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_quizwiz_Quizmain_getincorrect2(
        JNIEnv *env, jobject thiz){


    //srand(time(0));

    //rand_num2 = rand() % sentences.size();
    //while(rand_num2 == rand_num1 || rand_num2 == rand_num){
    //rand_num2 = rand() % sentences.size();}

    sentence2 = "[\"History: Who was the first person to break the sound barrier\", \"Chuck Yeager\"]";
    found2 = sentence2.find("\","); //Find instance of ','
    ans2 = sentence2.substr(found2 + 4, sentence2.length()); // Taking subtring from ','+3 to the end

    found_quote2 = ans2.find('\"'); //Finding next "
    incorrect2 = ans2.substr(0, found_quote2); //Taking a new substring from initial ans to the found_quote


    return env->NewStringUTF(incorrect2.c_str());

}

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_quizwiz_Quizmain_getincorrect3(
        JNIEnv *env, jobject thiz){


    //srand(time(0));

    //rand_num3 = rand() % sentences.size();
    //while(rand_num3 == rand_num2 || rand_num3 == rand_num1 || rand_num3 == rand_num){
    //rand_num3 = rand() % sentences.size();}

    sentence3 = "[\"History: Who was the first person to fly across the english channel\", \"louis bleriot\"]";
    found3 = sentence3.find("\","); //Find instance of ','
    ans3 = sentence3.substr(found3 + 4, sentence3.length()); // Taking subtring from ','+3 to the end

    found_quote3 = ans3.find('\"'); //Finding next "
    incorrect3 = ans3.substr(0, found_quote3); //Taking a new substring from initial ans to the found_quote


    return env->NewStringUTF(incorrect3.c_str());

}