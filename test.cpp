// Your First C++ Program

#include <bits/stdc++.h>
using namespace std;


int main() {
    

    std::vector<int> cloud_supply;
    std::vector<int> broker_demand;

   
   //test 1
    // cloud_supply={10,20,32,38};
    // broker_demand={35,30,23,12};

    // vector<vector<int>> price_matrix{{5,10,7,5},{3,13,8,7},{7,12,9,9},{8,15,5,8}};

   //test 2

    // cloud_supply={10,20};
    // broker_demand={20,10};

    
    vector<vector<float>> price_matrix(4);
 	int c_count=13;
    int b_count=4;
    
    
    //read input from file
    std::ifstream in("input.txt");

    for (int i = 0; i < b_count; ++i)
    {
    	for (int j = 0; j < c_count; ++j)
    	{   
    		float temp;
    		in>>temp;
            cout<<temp<<" ";
            cout<<"\ni:"<<i<<"=";
    		price_matrix[i].push_back(temp);
    	}
    }
    in.close();
}
