
#include <bits/stdc++.h>
using namespace std;


void  show_vm_allotments(vector<vector<int>> vm_alloted_to_broker,int c_count,int b_count,vector<int> broker_demand){
	  cout<<"          ";
	  for (int i = 0; i < c_count; ++i){
	  	cout<<"|Cloud "<<i+1<<"    ";
	  }
	  cout<<"\n";
	  for (int i = 0; i < c_count; ++i){
	    	cout<<"Broker "<<i+1<<" |";
	    	for (int j = 0; j <b_count; ++j){
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
    
    float price_matrix[c_count][b_count];
    vector<vector<float>> vec( c_count , vector<float> (b_count));
   
    for (int i = 0; i <c_count; ++i){
    	in>>temp;
    	cloud_supply.push_back(temp);
    }
     for (int i = 0; i <b_count; ++i){
    	in>>temp;
    	broker_demand.push_back(temp);	
    }

    for (int i = 0; i < b_count; ++i){
    	for (int j = 0; j < c_count; ++j){   
    		float temp;
    		in>>temp;
    		//price_matrix[i].push_back(temp);
    		price_matrix[j][i]=temp;
    	}
    }
    in.close();

    //''''''''''read input from file ends

	std::vector<int> copy_broker_demand=broker_demand;
	cout<<"price offered by cloud to brokers:\n\n         ";


	for (int i = 0; i < c_count; ++i){
			cout<<"|Broker "<<i+1<<"    ";
	}
	cout<<"\n";

	for (int i = 0; i < c_count; ++i){
			    	cout<<"Cloud "<<i<<" |";
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
	    
	float cloud_preference[c_count][b_count];

			
	 float c_price_matrix[c_count][b_count];
	 memcpy (cloud_preference,price_matrix,c_count*b_count*sizeof(float));

	memcpy (c_price_matrix,price_matrix,c_count*b_count*sizeof(float));

	
	   

	//creating cloud preferencr list    
	for (int i = 0; i <b_count; ++i){
	    	/* code */
	    	//sort(cloud_preference[i].begin(), cloud_preference[i].end()); 
	    	 sort(cloud_preference[i], cloud_preference[i] + b_count);
	    	 reverse(cloud_preference[i], cloud_preference[i] + b_count);
	    	for (int j = 0; j < c_count; ++j)
	    	{
	    	    for (int k = 0; k < c_count; ++k)
	    	    {
	    	    	if(cloud_preference[i][j]==c_price_matrix[i][k])
	    	    		{
	    	    			cloud_preference[i][j]=k;
	    	    		   	c_price_matrix[i][k]=0;
	    	    		   	break;
	    	    		   }
	    	    }
	    	}
	}

	// prining broker preference
	cout<<"\ncloud preference based on price\n";

	for (int i = 0; i < c_count; ++i){
		    	cout<<"Cloud"<<i+1<<" : ";
		    	for (int j = 0; j <b_count; ++j){
		    		cout<<"B"<<(cloud_preference[i][j]+1)<<" ";
		    	}
		    	cout<<"\n";
	}

	std::vector<int> cloud_current_pref(c_count,0);

	std::vector<int> pending_demand_br=broker_demand;
	vector<vector<int>> request_list(b_count);


	vector<vector<int>> vm_alloted_to_broker( c_count , vector<int> (b_count, 0));

	//allotment of vm to broker by cloud provider in each iteration
	for(int i = 0; i <b_count+c_count; ++i){    
		  // saving broker request in each iteration
		  int no_request=0;
		    int all_Demand=0;
			 for (int x = 0; x < b_count; ++x)
                    {
                        all_Demand+=pending_demand_br[x];
                    }   
		  for (int cl = 0; cl < b_count; ++cl){
		      		cout<<"\nbroker "<<cl+1<<" has : demand of  :"<<pending_demand_br[cl];
	              if(cloud_supply[cl]>0){
		      		no_request++;
		      			
		      	 	int broker_id=cloud_preference[cl][cloud_current_pref[cl]];
					//cout<<"cloudid:"<<broker_id+1<<" ";
			        cloud_current_pref[cl]++;
			      	request_list[broker_id].push_back(cl);
		      		}
		  }

		  if(no_request==0 or all_Demand==0)
		    	{
		    		cout<<"\n\n******ALL BROKER DEMAND MET WITH STABLE ALLOTMENT********\n\nfinal allotment\n";

		    		show_vm_allotments(vm_alloted_to_broker,c_count,b_count,broker_demand);
		    		break;}

		  cout<<"\n------------------------------------------------\niteration:"<<i+1<<"\n";


			//sort the request list
		  for (int i = 0; i < c_count; ++i){ 
		      for(int j = 0; j < request_list[i].size(); ++j){

		      	  int min=request_list[i][j]; //broker id
		      	  //cout<<"\nmax:"<<max;
		      	  //int max=0;
			      for (int k = j+1; k <request_list[i].size() ; ++k){
		      			if(price_matrix[min][i]>price_matrix[request_list[i][k]][i]){	
		      					//swap
		      					request_list[i][j]=request_list[i][k];
		      					request_list[i][k]=min;
		      					min=request_list[i][j];
		      			}
			      }
		      }   	
		  }

		  cout<<"\n\nRequest list sorted as per cloud preference:\n ";

		      for (int i = 0; i < c_count; ++i){ 
		      	cout<<"\nBroker "<<i+1<<": ";
		      	for (int j = 0; j < request_list[i].size(); ++j){
		      		cout<<"C"<<request_list[i][j]+1<<" ";
		      	}   	
		      }


	        //broker checking request and accepting cloud preference based on his priority
			      
		  for (int i = 0; i < b_count; ++i){
		      		pending_demand_br[i]=broker_demand[i];

		      		 cout<<"\n\nboker "<<i+1<<"  allotment\n";

	      		      
		      		 

		      		 //(taking back previous allotment and reassigning again)
		      		for(int m = 0; m < request_list[i].size(); ++m){
		      			
		      			
		      			cloud_supply[request_list[i][m]]+=vm_alloted_to_broker[i][request_list[i][m]];
		      			//cout<<"\nSatish:"<<m+1<<":"<<vm_alloted_to_broker[i][request_list[i][m]]<<endl;
		      			vm_alloted_to_broker[i][request_list[i][m]]=0;
		      		}	      			
	                
		      		for (int j = 0; j < request_list[i].size(); ++j){
	                     
		      		if(pending_demand_br[i]>0 ){
		      			
		      			int supply=cloud_supply[request_list[i][j]];
		      			//cout<<"\npending demans for broker"<<i+1<<"is:"<<pending_demand_br[i];
		      			cout<<"\nC"<<request_list[i][j]+1<<" supply is :"<<supply<<"\n";
		      			if(supply<pending_demand_br[i])
			      		{
		
			      			pending_demand_br[i]-=supply;			      
			      			cloud_supply[request_list[i][j]]=0;
			      			vm_alloted_to_broker[i][request_list[i][j]]=supply;
			      			cout<<"\n remianing request for broker is "<<i+1<<" is:"<<pending_demand_br[i];
			      		}
			      		else
			      		{  
			      			
			      				//broker_demand[request_list[i][j]]=supply+vm_alloted_to_broker[request_list[i][j]][i]-pending_demand_br[i];
				      			vm_alloted_to_broker[i][request_list[i][j]]=pending_demand_br[i];
				      							      			
				      			cloud_supply[request_list[i][j]]=supply-pending_demand_br[i];
				      			cout<<"\n remianing request for broker "<<request_list[i][j]+1<<":0\n";
				      			pending_demand_br[i]=0;
			      	    }
		      		}
		      		else{
		      				//removing broker preference if no more vm to allot
		      				request_list[i].size();
		      				request_list[i].erase(request_list[i].begin() + j, request_list[i].end());
		      				break;
		      		}
		      		}    	
		  }

	      cout<<"\n vms alloted in "<<i+1<<" :iteration\n\n";
	      show_vm_allotments(vm_alloted_to_broker,c_count,b_count,broker_demand);
	      cout<<"cloud supply after "<<i+1<<" allotment:\n";
		  for (int i = 0; i < b_count; ++i){
					cout<<"  C"<<i+1<<":"<<cloud_supply[i];
		  }
	}

	    return 0;
}