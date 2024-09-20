#include<iostream>
#include<bits/stdc++.h>
using namespace std;

bool comparator(pair<string,vector<int>> pr1,pair<string,vector<int>> pr2){
    return pr1.second[0] < pr2.second[0];
}

int max_index(vector<pair<string,vector<int>>> vec){
    int ans = -1;
    int temp_var = INT_MAX;
    for(int i=0;i<vec.size();i++){
        if(vec[i].second[2] <= temp_var){
            temp_var = vec[i].second[2];
            ans = i;
        }
    }
    return ans;
}

bool comparator1(pair<string,vector<int>> pr1,pair<string,vector<int>> pr2){
    return pr1.second[2] < pr2.second[2];
}

void draw_gantt(vector<pair<string,vector<int>>> pr){
    cout<<"------------------------------------------------------------------------------------------"<<endl;
    cout<<"    ";
    for(int i=0;i<pr.size();i++){
        cout<<pr[i].first<<" \t\t   ";
    }
    cout<<"\n";
    for(int i=0;i<pr.size();i++){
        cout<<pr[i].second[0]<<"  \t  "<<pr[i].second[1]<<"  ";
    }
    cout<<endl;
   cout<<"------------------------------------------------------------------------------------------"<<endl;
}

int main()
{
    int n;
    cout<<"Enter total number of processes : "<<endl;
    cin>>n;
    vector<string> process_name;
    vector<int> arrival_time;
    vector<int> burst_time;
    vector<int> priority;
    for(int i=0;i<n;i++){
        string name;
        int at,bt,pr;
        cout<<"Enter the name of process "<<i+1<<" : "<<endl;
        cin>>name;
        process_name.push_back(name);
        cout<<"Enter the arrival time of the process "<<name<<" : "<<endl;
        cin>>at;
        arrival_time.push_back(at);
        cout<<"Enter the burst time of the process "<<name<<" : "<<endl;
        cin>>bt;
        burst_time.push_back(bt);
        cout<<"Enter the priority of the process "<<name<<" : "<<endl;
        cin>>pr;
        priority.push_back(pr);
    }
    cout<<"-----------------------------------"<<endl;
    cout<<"The input in tabular format is : "<<endl;
    cout<<"-----------------------------------"<<endl;
    cout<<"Process Name"<< "\t" <<"Arrival Time" << "\t" << "Burst Time" << "\t" <<"Priority"<<endl;
    for(int i=0;i<n;i++){
        cout<<process_name[i]<<" \t\t "<<arrival_time[i]<<" \t\t "<<burst_time[i]<<" \t\t "<<priority[i]<<endl;
    }
    int max_arrival = *max_element(arrival_time.begin(),arrival_time.end());
    vector<pair<string,vector<int>>> vec;
    for(int i=0;i<n;i++){
        pair<string,vector<int>> pr;
        pr.first = process_name[i];
        pr.second.push_back(arrival_time[i]);
        pr.second.push_back(burst_time[i]);
        pr.second.push_back(priority[i]);
        vec.push_back(pr);
    }
    sort(vec.begin(),vec.end(),comparator);
    // for(auto ele:vec)
    // {
    //     cout<<ele.first<<" "<<ele.second[0]<<" "<<ele.second[1]<<" "<<ele.second[2]<<endl;
    // }
    vector<pair<string,vector<int>>> push_vec;
    int tt=0;
    while(tt <= max_arrival && vec.size() > 0)
    {
        int i=0,j;
        int temp = max_index(vec);
        for(int j=i+1;j<vec.size();j++){
            // cout<<"---------"<<endl;
            // cout<<i<<" "<<j<<endl;
            // cout<<temp<<endl;
            // cout<<"---------"<<endl;
            if(i == temp){
                tt = tt + vec[i].second[1];
                pair<string,vector<int>> lc_pr;
                lc_pr.first = vec[i].first;
                lc_pr.second.push_back(vec[i].second[0]);
                lc_pr.second.push_back(tt);
                lc_pr.second.push_back(vec[i].second[2]);
                push_vec.push_back(lc_pr);
                vec.erase(vec.begin() + temp);
                break;
            }
            if(vec[j].second[0] <= tt + vec[i].second[1])
            {
                int help = vec[j].second[0] - vec[i].second[0];
                tt = tt + help;
                vec[i].second[1] = vec[i].second[1] - help;
                pair<string,vector<int>> pr;
                pr.first = vec[i].first;
                pr.second.push_back(vec[i].second[0]);
                pr.second.push_back(tt);
                pr.second.push_back(vec[i].second[2]);
                push_vec.push_back(pr);
            }
            i++;
        }
    }
    // cout<<"------------------------------"<<endl;
    // for(auto ele:push_vec){
    //     cout<<ele.first<<" "<<ele.second[0]<<" "<<ele.second[1]<<" "<<ele.second[2]<<endl;
    // }
    // cout<<"------------------------------"<<endl;
    // for(auto ele:vec){
    //     cout<<ele.first<<" "<<ele.second[0]<<" "<<ele.second[1]<<" "<<ele.second[2]<<endl;
    // }
    sort(vec.begin(),vec.end(),comparator1);
    for(auto ele:vec){
        pair<string,vector<int>> ins_pr;
        ins_pr.first = ele.first;
        ins_pr.second.push_back(tt);
        tt = tt + ele.second[1];
        ins_pr.second.push_back(tt);
        ins_pr.second.push_back(ele.second[2]);
        push_vec.push_back(ins_pr);
    }
    draw_gantt(push_vec);
}