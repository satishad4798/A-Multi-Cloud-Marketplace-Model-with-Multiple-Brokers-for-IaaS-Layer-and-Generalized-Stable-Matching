// Your First C++ Program

#include <bits/stdc++.h>
using namespace std;


// vector<int>  broker_preferencr(vector<int> price){
// 	return     sort(price.begin(), price.end()); 
// }

// try
void  show_vm_allotments(vector<vector<int>> vm_alloted_to_broker,int c_count,int b_count,vector<int> broker_demand)
{
	   cout<<"          ";
					  for (int i = 0; i < c_count; ++i)
					  {
					  	cout<<"|Cloud "<<i+1<<"    ";
					  }
					  cout<<"\n";
					    for (int i = 0; i < c_count; ++i)
					    {
					    	cout<<"Broker "<<i+1<<" |";
					    	for (int j = 0; j <b_count; ++j)
					    	{
					    		cout<<"    "<<vm_alloted_to_broker[i][j]<<"      |";
					    		
					    	}
					    	cout<<"\n";
					    }  
}
int main() {
    

   
	int c_count ;// no of cloud provider
    int b_count; //no of brokers
    int temp;
   	
 	
    std::vector<int> cloud_supply;
    std::vector<int> broker_demand;

    //''''''''''read input from file
    std::ifstream in("input2.txt");
    in>>b_count;
    in>>c_count;
    
    vector<vector<float>> price_matrix(b_count);

   
    for (int i = 0; i <c_count; ++i){
    	in>>temp;
    	cloud_supply.push_back(temp);
    }
     for (int i = 0; i <b_count; ++i){
    	in>>temp;
    	broker_demand.push_back(temp);	
    }

    for (int i = 0; i < b_count; ++i)
    {
    	for (int j = 0; j < c_count; ++j){   
    		float temp;
    		in>>temp;
    		price_matrix[i].push_back(temp);
    	}
    }
    in.close();

    //''''''''''read input from file ends

  std::vector<int> copy_broker_demand=broker_demand;
  cout<<"price offered by cloud to brokers:\n\n         ";


  for (int i = 0; i < c_count; ++i)
  {
  	cout<<"|Cloud "<<i+1<<"    ";
  }
  cout<<"\n";
    for (int i = 0; i < c_count; ++i)
    {
    	cout<<"Broker "<<i<<" |";
    	for (int j = 0; j <b_count; ++j)
    	{
    		cout<<"    "<<price_matrix	[i][j]<<"      |";
    		
    	}
    	cout<<"\n";
    }

    std::vector<int> broker_max_price{8,12,15,7};

    cout<<"\n\nMax price at which broker can buy:\n";
    for (int i = 0; i < b_count; ++i) {
    	cout<<"Broker "<<i+1<<" :"<<broker_max_price[i]<<"\n";
    }
    cout<<"\n\nbroker demand::\n";
     for (int i = 0; i < b_count; ++i) {
    	cout<<"Broker "<<i+1<<" :"<<broker_demand[i]<<"\n";
    }
      cout<<"\ncloud supply::\n";
     for (int i = 0; i < b_count; ++i) {
    	cout<<"Broker "<<i+1<<" :"<<cloud_supply[i]<<"\n";
    }
    
    vector<std::vector<float>> broker_preference;
    broker_preference=price_matrix;
     vector<std::vector<float>> c_price_matrix=price_matrix;
    // broker preferencr list
    
    for (int i = 0; i <b_count; ++i)
    {
    	/* code */
    	sort(broker_preference[i].begin(), broker_preference[i].end()); 
    	
    	for (int j = 0; j < c_count; ++j)
    	{
    	    for (int k = 0; k < c_count; ++k)
    	    {
    	    	if(broker_preference[i][j]==c_price_matrix[i][k])
    	    		{
    	    			broker_preference[i][j]=k;
    	    		   	c_price_matrix[i][k]=0;
    	    		   	break;
    	    		   }
    	    }
    	}

    }

    // prining broker preference
    cout<<"\nbroker preference based on price\n";
    for (int i = 0; i < c_count; ++i){

    	cout<<"Broker"<<i+1<<" : ";
    	for (int j = 0; j <b_count; ++j){
    		cout<<"c"<<broker_preference	[i][j]<<" ";
    	}
    	cout<<"\n";
    }

std::vector<int> br_current_pref(b_count,0);


vector<std::vector<int>> cloud_preference{{4,3,2,1},{4,2,3,1},{3,2,1,4},{3,4,2,1}};

std::vector<int> residual_vms_cloud=cloud_supply;
    vector<vector<int>> request_list(c_count);


vector<vector<int>> vm_alloted_to_broker( c_count , vector<int> (b_count, 0));

for (int i = 0; i <c_count+5; ++i)
{
	      
	      // saving broker request in each iteration
	      int no_request=0;
	      for (int br = 0; br < b_count; ++br)
	      {
	      	
              if(broker_demand[br]>0)
	      	{
	      		no_request++;
	      	//	cout<<"\nbroker :"<<br+1<<" demand :"<<broker_demand[br];
	      	 int cloud_id=broker_preference[br][br_current_pref[br]];
	      				//cout<<"cloudid:"<<cloud_id+1<<" ";
	      		        br_current_pref[br]++;
	      		      	request_list[cloud_id].push_back(br);}

	    }
	    if(no_request==0)
	    	{
	    		cout<<"\n\n******ALL BROKER DEMAND MET WITH STABLE ALLOTMENT********\n\nfinal allotment\n";

	    		show_vm_allotments(vm_alloted_to_broker,c_count,b_count,broker_demand);
	    		break;}

	cout<<"\n------------------------------------------------\niteration:"<<i+1<<"\n";

//broker_demand=copy_broker_demand;
		  //sort the request list
		      for (int i = 0; i < c_count; ++i)
		      { 
		      	
			      	for (int j = 0; j < request_list[i].size(); ++j)
			      	{
			      		int max=request_list[i][j]; //broker id
			      		//cout<<"\nmax:"<<max;
			      		//int max=0;
			      		for (int k = j+1; k <request_list[i].size() ; ++k) //error for temp
			      		{
			      			if(price_matrix[max][i]<price_matrix[request_list[i][k]][i])
			      				{
			      					
			      					//swap
			      					request_list[i][j]=request_list[i][k];

			      					//cout<<"\nSatish1:"<<request_list[i][j];

			      					request_list[i][k]=max;
			      				//	cout<<"\nSatish2: "<<request_list[i][k];
			      					max=request_list[i][j];

			      				}
			      		}
			      	}
		      	
		      }

			 cout<<"\n\nRequest list sorted as per cloud preference:\n ";

					      for (int i = 0; i < c_count; ++i)
					      { cout<<"\ncloud "<<i+1<<": ";
					      	for (int j = 0; j < request_list[i].size(); ++j)
					      	{
					      		cout<<"B"<<request_list[i][j]+1<<" ";
					      	}
					      	
					      }


        //allotment
		      // residual_vms_cloud=cloud_supply;(wrong)
		      // broker_demand=copy_broker_demand;(wrong)
		        for (int i = 0; i < c_count; ++i)
		      {
		      			residual_vms_cloud[i]=cloud_supply[i];

		      			 //(taking back previous allotment and reassigning again)
		      			for (int m = 0; m < request_list[i].size(); ++m){
		      				broker_demand[request_list[i][m]]+=vm_alloted_to_broker[request_list[i][m]][i];
		      				 vm_alloted_to_broker[request_list[i][m]][i]=0;
		      			}
		      			

                   //  cout<<"\ncloud "<<i+1<<"  allotment\n";
		      	for (int j = 0; j < request_list[i].size(); ++j)
		      	{
                          //cout<<"\ninside broker list\n";
		      		if(residual_vms_cloud[i]>0 ){
		      			//cout<<"\ninside if condtion\n";

		      			int demand=broker_demand[request_list[i][j]];
		      			//cout<<"\nB:"<<request_list[i][j]+1<<" demand:"<<demand<<"\n";
		      			if(demand<residual_vms_cloud[i])
			      		{
			      			//cout<<"\n remianing request for broker :"<<request_list[i][j]+1<<"0\n";
			      			

			      			residual_vms_cloud[i]-=demand;
			      			//cout<<"\nresidual_vms_cloud[i]"<<residual_vms_cloud[i];

			      			// update broker demand if vm was taken back (if doubt ask)
			      			
			      			// broker_demand[request_list[i][j]]=demand+vm_alloted_to_broker[request_list[i][j]][i]-present_allotment;   //previous allotment and present allotment
			      			broker_demand[request_list[i][j]]=0;

			      			vm_alloted_to_broker[request_list[i][j]][i]=demand;
			      		}
			      		else
			      		{   //allot only if not alloted
			      			
			      				broker_demand[request_list[i][j]]=demand+vm_alloted_to_broker[request_list[i][j]][i]-residual_vms_cloud[i];
					      						      			vm_alloted_to_broker[request_list[i][j]][i]=residual_vms_cloud[i];
					      						      			
					      						      			//cout<<"\nsatish"<<demand-residual_vms_cloud[i];
					      						      			broker_demand[request_list[i][j]]=demand-residual_vms_cloud[i];
					      						      		//	cout<<"\n remianing request for broker "<<request_list[i][j]+1<<":"<<broker_demand[request_list[i][j]]<<"\n";
					      			
					      						      			residual_vms_cloud[i]=0;
			      	    }

		      		}
		      		else 
		      			{

		      				request_list[i].size();
		      				request_list[i].erase(request_list[i].begin() + j, request_list[i].end());
		      				break;
		      			}
		      	}
		      	
		      }
		     cout<<"\n vms alloted in "<<i+1<<" :iteration\n\n";
		    show_vm_allotments(vm_alloted_to_broker,c_count,b_count,broker_demand);
		    cout<<"Broker demand after "<<i<<" allotment:\n";
				for (int i = 0; i < b_count; ++i){
					cout<<"  B"<<i+1<<":"<<broker_demand[i];
				}

}

    return 0;
}