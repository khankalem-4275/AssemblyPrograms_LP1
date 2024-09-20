#include<iostream>
#include<bits/stdc++.h>
#include<queue>
#include<string>

using namespace std;

bool comparator(pair<string,vector<int>> pr1, pair<string,vector<int>> pr2){
    return pr1.second[0] < pr2.second[0];
}

void print_queue(queue<pair<string,vector<int>>> q){
    queue<pair<string,vector<int>>> q1 = q;
    while(!q1.empty()){
        cout<<q1.front().first<<" "<<q1.front().second[0]<<" "<<q1.front().second[1]<<endl;
        q1.pop();
    }
    cout<<"\n";
}

int main()
{
    int n;
    cout<<"Enter the no. of processes : "<<endl;
    cin>>n;
    vector<pair<string,vector<int>>> vec;
    for(int i=0;i<n;i++){
        pair<string,vector<int>> pr;
        string name;
        cout<<"Enter the name of the process : "<<endl;
        cin>>name;
        int at,bt;
        cout<<"Enter the arrival time of the process "<<name<<" : "<<endl;
        cin>>at;
        cout<<"Enter the burst time of the process "<<name<<" : "<<endl;
        cin>>bt;
        pr.first = name;
        pr.second.push_back(at);
        pr.second.push_back(bt);
        vec.push_back(pr);
    }
    int tq;
    cout<<"Enter the time quantum for round robin : "<<endl;
    cin>>tq;
    sort(vec.begin(),vec.end(),comparator);
    queue<pair<string,vector<int>>> q;
    vector<pair<string,vector<int>>> result;
    int help = 0;
    int i = 0;
    int tt = tq;
    while(i < vec.size()){
        while(help < vec.size() && vec[help].second[0] <= tt){
            q.push(vec[help]);
            help++;
        }
        if(help < n){
        if(vec[i].second[1] >= tq){
            vec[i].second[1] = vec[i].second[1] - tq;
            tt = tt + tq;
            if(vec[i].second[1] > 0){
                q.push(vec[i]);
            }
        }else{
            tt = tt + vec[i].second[1];
            vec[i].second[1] = 0;
            vec.erase(vec.begin() + i);
        }
        if(!q.empty()){
            pair<string,vector<int>> pr = q.front();
            if(pr.second[1] >= tq){
                pr.second[1] = tq;
                result.push_back(pr);
            }
            else{
                result.push_back(pr);
            }
            q.pop();
        }
    }
        i++;
    }
    while(!q.empty()){
        pair<string,vector<int>> pr = q.front();
        if(pr.second[1] > tq){
            q.front().second[1] = tq;
            result.push_back(q.front());
            pr.second[1] = pr.second[1] - tq;
            q.push(pr);
        }
        else{
            result.push_back(pr);
        }
        q.pop();
    }

    print_queue(q);

    // cout<<"\n\n";
    for(auto ele:result){
        cout<<ele.first<<" "<<ele.second[0]<<" "<<ele.second[1]<<endl;
    }
}