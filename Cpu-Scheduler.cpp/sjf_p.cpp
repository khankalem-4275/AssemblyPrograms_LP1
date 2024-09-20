#include<iostream>
#include<bits/stdc++.h>

using namespace std;

bool comparator(pair<string,vector<int>> pr1,pair<string,vector<int>> pr2){
    if(pr1.second[0] == pr2.second[0]){
        return pr1.second[2] < pr2.second[2];
    }
    return pr1.second[0] < pr2.second[0];
}

bool comparator1(pair<string,vector<int>> pr1, pair<string,vector<int>> pr2){
    return pr1.second[1] < pr2.second[1];
}

void draw_gantt(){};

int main()
{
    int n;
    cout<<"Enter the number of processes : "<<endl;
    cin>>n;
    vector<pair<string,vector<int>>> vec;
    for(int i=0;i<n;i++){
        string name;
        cout<<"Enter the name of the process : "<<endl;
        cin>>name;
        int at,bt;
        cout<<"Enter the arrival time of the process : "<<name<<" : "<<endl;
        cin>>at;
        cout<<"Enter the burst time of the process : "<<name<<" : "<<endl;
        cin>>bt;
        pair<string,vector<int>> pr;
        pr.first = name;
        pr.second.push_back(at);
        pr.second.push_back(bt);
        vec.push_back(pr);
    }    
    sort(vec.begin(),vec.end(),comparator);
    vector<int> arrived;
    vector<pair<string,int>> res;
    arrived.push_back(vec[0].second[1]);
    int tt = 0;
    for(int i=1;i<vec.size();i++){
        cout<<arrived[0]<<endl;
        int diff = vec[i].second[0] - tt;
        arrived[0] = arrived[0] - diff;
        if(arrived[0] > vec[i].second[1]){
            pair<string,int> ins;
            ins.first = vec[i-1].first;
            ins.second = diff;
            res.push_back(ins);
            arrived.insert(arrived.begin(),vec[i].second[1]);
            vec[i-1].second[1] = vec[i-1].second[1] - diff;
        }
        else{
            pair<string,int> ins;
            ins.first = vec[i-1].first;
            ins.second = arrived[0] + diff;
            res.push_back(ins);
            tt = tt + arrived[0];
            arrived.erase(arrived.begin());
            vec[i-1].second[1] = vec[i-1].second[1] - (diff + arrived[0]);
            vec.erase(vec.begin() + i - 1);
            i--;
            arrived.insert(arrived.begin(),vec[i].second[1]);
        }
        tt = tt + diff;
    }
    if(!vec.empty()){
        sort(vec.begin(),vec.end(),comparator1);
    }
    for(auto ele:vec){
        pair<string,int> ins_pr;
        ins_pr.first = ele.first;
        ins_pr.second = ele.second[1];
        res.push_back(ins_pr);
    }
    for(auto ele:res){
        cout<<ele.first<<" "<<ele.second<<endl;
    }
   // call to draw_gantt...
}