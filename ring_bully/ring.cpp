#include<iostream>
#include<bits/stdc++.h>
using namespace std;

bool comparator(pair<string,vector<int>> pr1, pair<string,vector<int>> pr2){
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
    cout<<"Enter the total number of processes : "<<endl;
    cin>>n;
    vector<pair<string,vector<int>>> vec;
    for(int i=0;i<n;i++){
        string name;
        int pr;
        cout<<"Enter the process name : "<<endl;
        cin>>name;
        cout<<"Enter the priority of the process : "<<name<<" : "<<endl;
        cin>>pr;
        pair<string,vector<int>> ins_pr;
        ins_pr.first = name;
        ins_pr.second.push_back(pr);
        ins_pr.second.push_back(1);
        vec.push_back(ins_pr);
    }
    sort(vec.begin(),vec.end(),comparator);
    cout<<"The co-ordinator process is : "<<vec[vec.size() - 1].first<<" with the priority "<<vec[vec.size() - 1].second[0]<<endl;
    vector<pair<string,int>> traverse;
    string f_name;
    cout<<"Enter the process name which detects the failure of the co-ordinator process : "<<endl;
    cin>>f_name;
    // int temp = ret(f_name,vec);
    vec[vec.size() - 1].second[1] = 0;
    cout<<"After passing through the process in a logical ring are : "<<endl;
    for(int i=0;i<vec.size();i++){
        if(vec[i].second[1] == 1){
             pair<string,int> pr;
            pr.first = vec[i].first;
            pr.second = vec[i].second[0];
            traverse.push_back(pr);
        }
        cout<<"After passing through : "<<vec[i].first<<" : ";
        for(auto ele:traverse){
            cout<<"( "<<ele.first<<" "<<ele.second<<" ) ";
        }
        cout<<"\n";
    }
    cout<<"==========================="<<endl;
    cout<<"The election co-ordinator after election is : "<<traverse[traverse.size() - 1].first<<endl;
    cout<<"==========================="<<endl;
}