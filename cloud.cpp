// Your First C++ Program

#include <bits/stdc++.h>
using namespace std;


// vector<int>  broker_preferencr(vector<int> price){
// 	return     sort(price.begin(), price.end()); 
// }

// try
int main() {
    

    std::vector<int> cloud_supply;
    std::vector<int> broker_demand;

    cloud_supply={10,20,32,38};
    broker_demand={35,30,23,12};

    int c_count=cloud_supply.size();
    int b_count=broker_demand.size();

    vector<vector<int>> price_matrix{{5,10,7,5},{3,13,8,7},{7,12,9,9},{8,15,5,8}};


   
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

    vector<std::vector<int>> broker_preference;
    broker_preference=price_matrix;
     vector<std::vector<int>> c_price_matrix=price_matrix;
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
	      		cout<<"\nbroker :"<<br+1<<" demand :"<<broker_demand[br];
	      	 int cloud_id=broker_preference[br][br_current_pref[br]];
	      				//cout<<"cloudid:"<<cloud_id+1<<" ";
	      		        br_current_pref[br]++;
	      		      	request_list[cloud_id].push_back(br);}

	    }
	    if(no_request==0)
	    	{
	    		cout<<"\n******ALL BROKER DEMAND MET WITH STABLE ALLOTMENT********";
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
		      			

                     cout<<"\ncloud "<<i+1<<"  allotment\n";
		      	for (int j = 0; j < request_list[i].size(); ++j)
		      	{
                          //cout<<"\ninside broker list\n";
		      		if(residual_vms_cloud[i]>0 ){
		      			//cout<<"\ninside if condtion\n";

		      			int demand=broker_demand[request_list[i][j]];
		      			cout<<"\nB:"<<request_list[i][j]+1<<" demand:"<<demand<<"\n";
		      			if(demand<residual_vms_cloud[i])
			      		{
			      			cout<<"\n remianing request for broker :"<<request_list[i][j]+1<<"0\n";
			      			

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
					      						      			cout<<"\n remianing request for broker "<<request_list[i][j]+1<<":"<<broker_demand[request_list[i][j]]<<"\n";
					      			
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

		    cout<<"\n vms alloted in "<<i+1<<" :iteration\n\n         ";

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


				cout<<"broker demand after "<<i<<" iteration:\n";
				for (int i = 0; i < b_count; ++i){
					cout<<"  B"<<i+1<<":"<<broker_demand[i];
				}


		// for (int br = 0; br < b_count; ++br)
		//     {

		//     	// // broker i making request his jth preference
		//     	// cout<<[broker_preference[b_count]];
		    	
		//     	 int cloud_id=broker_preference[br][br_current_pref[br]];
		//     	 //cout<<cloud_id_request;
		//     	 cout<<"\n\nbroker:"<<br+1<<" requesting:"<<cloud_id+1;
		//     	// cout<<cloud_id_request;
		//     	if(broker_demand[br]>0)
		//     	{
		//     		// direct allotment ,no need for override existing allotment
		//     		if(broker_demand[br]<=residual_vms_cloud[cloud_id] or residual_vms_cloud[cloud_id]==cloud_supply[cloud_id] )  
		//     		{
		//     			 residual_vms_cloud[cloud_id]-=broker_demand[br];
		//     			cout<<"\nallotemt to broker:"<<br+1<<"by cloud :"<<cloud_id+1;
		//     			br_current_pref[br]++;
		//     			broker_demand[br]-=residual_vms_cloud[cloud_id];
		//     			allotment[cloud_id].push_back(br+1);

		//     		}
		//     		else //override the allotment
		//     		{

		//     			residual_vms_cloud[cloud_id]=0;
		//     			cout<<"\nallotemt to broker:"<<br+1<<"by cloud :"<<broker_preference[br][b_count]+1;
		//     			broker_demand[br]=broker_demand[br]-residual_vms_cloud[cloud_id];
		//     			b_count++;

		//     		}
		//     	}
		    		

		//     }
		   

			

}

cout<<"\n";
for(int i=0;i<c_count;i++){
	for(int j=0;j<c_count;j++){
		cout<<cloud_preference[i][j]<<" ";
	}
	cout<<"\n";

}
		  
// for (int i = 0; i < c_count; ++i)
// 		    {
// 		    	for (int j = 0; j < b_count; ++j)
// 		    	{
// 		    		/* code */

// 		    	}
// 		    	/* code */
// 		    }		    
    


    return 0;
}