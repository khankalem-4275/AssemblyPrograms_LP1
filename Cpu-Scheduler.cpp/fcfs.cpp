#include<bits/stdc++.h>
#include<iostream>
#include<string>
using namespace std;

bool comparator(pair<string,pair<int,int>> pr1,pair<string,pair<int,int>> pr2){
    return pr2.second.first > pr1.second.first;
}

void draw_gantt(vector<string> pn,vector<pair<int,int>> ct){
    cout<<"----------------------------------------"<<endl;
    for(int i=0;i<pn.size();i++){
        if(ct[i+1].second == 1){
            cout<<"  \t";
        }
        cout<<"   "<<pn[i]<<"   ";
    }
    cout<<endl;
    for(int i=0;i<ct.size();i++){
        // if(ct[i].second == 1){
        //     cout<<"\t";
        //     continue;
        // }
        cout<<ct[i].first<<"\t";
    }
    cout<<endl;
    cout<<"----------------------------------------"<<endl;
}

int main(){
    int n;
    cout<<"Enter the number of processes : ";
    cin>>n;
    vector<string> process_name;
    vector<int> arrival_time;
    vector<int> burst_time;
    for(int i=1;i<=n;i++){
        string inp;
        int at,bt;
        cout<<"Enter the name of process "<<i<<":";
        cin>>inp;
        process_name.push_back(inp);
        cout<<"Enter the arrival time of the process "<<inp<<":";
        cin>>at;
        arrival_time.push_back(at);
        cout<<"Enter the burst time of the process "<<inp<<":";
        cin>>bt;
        burst_time.push_back(bt);
    }
    cout<<"--------------------------------------------------"<<endl;
    cout<<"The user input in tabular format is : "<<endl;
    cout<<"Process_Name \t Arrival_Time \t Burst_Time"<<endl;
    for(int i=0;i<n;i++){
        cout<<process_name[i]<<" \t\t "<<arrival_time[i]<<" \t\t "<<burst_time[i]<<endl;
    }
    cout<<"--------------------------------------------------"<<endl;
    
    vector<pair<string,pair<int,int>>> vec;
    for(int i=0;i<n;i++){
        pair<string,pair<int,int>> pr;
        pr.first = process_name[i];
        pr.second.first = arrival_time[i];
        pr.second.second = burst_time[i];
        vec.push_back(pr);
    }
    sort(vec.begin(),vec.end(),comparator);
    for(int i=0;i<n;i++){
        process_name[i] = vec[i].first;
        arrival_time[i] = vec[i].second.first;
        burst_time[i] = vec[i].second.second; 
    }
    vector<int> completion_time;
    vector<pair<int,int>> gantt_vec;
    gantt_vec.push_back(make_pair(0,0));
    int tt=0;
    for(int i=0;i<burst_time.size();i++){
        if(tt < arrival_time[i]){
            tt = arrival_time[i];
            gantt_vec.push_back(make_pair(tt,1));
            tt = tt + burst_time[i];
            completion_time.push_back(tt);
            gantt_vec.push_back(make_pair(tt,0));
            continue;
        }
        tt = tt + burst_time[i];
        completion_time.push_back(tt); 
        gantt_vec.push_back(make_pair(tt,0));
    }
    vector<int> tat;
    for(int i=0;i<completion_time.size();i++){
        tat.push_back(completion_time[i] - arrival_time[i]);
    }
    vector<int> waiting_time;
    for(int i=0;i<tat.size();i++){
        waiting_time.push_back(tat[i] - burst_time[i]);
    }
    // cout<<"\n";
    // cout<<"------------"<<endl;
    // cout<<"Average turnaround time : "<<(float)accumulate(tat.begin(),tat.end(),0)/tat.size()<<endl;
    // cout<<"Average waiting time : "<<(float)(accumulate(waiting_time.begin(),waiting_time.end(),0))/waiting_time.size()<<endl;
    // cout<<"------------"<<endl;

    // for(auto ele:arrival_time){
    //     cout<<ele<<" ";
    // }
    // cout<<"\n";
    // for(auto ele:burst_time){
    //     cout<<ele<<" ";
    // }
    // cout<<"\n";
    // for(auto ele:completion_time){
    //     cout<<ele<<" ";
    // }
    // cout<<"\n";

    draw_gantt(process_name,gantt_vec);
}