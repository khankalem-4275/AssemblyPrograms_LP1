#include<iostream>
#include<bits/stdc++.h>
using namespace std;

bool comparator(pair<string,vector<int>> pr1,pair<string,vector<int>> pr2){
    return pr1.second[0] < pr2.second[0];
}

int ret(string name,vector<pair<string,vector<int>>> &vec){
    for(int i=0;i<vec.size();i++){
        if(vec[i].first == name){
            vec[i].second[1] = 0;
            return i;
        }
    }
    return -1;
}

int main()
{
    int n;
    cout<<"Enter the no. of processes :"<<endl;
    cin>>n;
    vector<pair<string,vector<int>>> vec;
    for(int i=0;i<n;i++){
        string name;
        int pr;
        pair<string,vector<int>> pair_ins;
        cout<<"Enter the name of the process : "<<endl;
        cin>>name;
        pair_ins.first = name;
        cout<<"Enter the priority of the process "<<name<<" : "<<endl;
        cin>>pr;
        pair_ins.second.push_back(pr);
        pair_ins.second.push_back(1);
        vec.push_back(pair_ins);
    }
    sort(vec.begin(),vec.end(),comparator);
    cout<<"=================================="<<endl;
    cout<<"The current co-ordinator is the process "<<vec[vec.size()-1].first<<" with the priority "<<vec[vec.size()-1].second[0]<<endl;
    vec[vec.size()-1].second[1] = 0;
    string f_name;
    cout<<"Enter the co-ordinator process name which has sent the election message to the co-ordinator : "<<endl;
    cin>>f_name;
    int temp = ret(f_name,vec);
    cout<<"Sending election messages to all processes higher in priority : "<<endl;
    while(temp < vec.size() - 2){
        for(int i=temp+1;i<vec.size();i++){
                if(vec[i].second[1] == 1){
                    cout<<"Sent an election message to process : "<<vec[i].first<<endl;
                    cout<<"Received an OK message from process : "<<vec[i].first<<endl;
                }
                else{
                    cout<<"Not sent an election message to process : "<<vec[i].first<<" as it is failed !"<<endl;
                    cout<<"OK message not received from process : "<<vec[i].first<<" as it is failed !"<<endl;
                }
        }
        temp++;
    }
    cout<<"=========================================="<<endl;
    cout<<"Thus the new co-ordinator is :"<<vec[temp].first<<endl;
    for(int i=0;i<temp;i++){
        cout<<vec[i].first<<" received an OK message !"<<endl;
    }
    cout<<"=========================================="<<endl;
}