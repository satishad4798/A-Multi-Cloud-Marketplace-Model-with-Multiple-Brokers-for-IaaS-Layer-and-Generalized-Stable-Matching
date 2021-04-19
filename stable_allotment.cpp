
#include <bits/stdc++.h>
using namespace std;


void  show_vm_allotments(vector<vector<int>> vm_alloted_to_broker,int c_count,int b_count,vector<int> broker_demand){
	  cout<<"          ";
	  for (int i = 0; i < c_count; ++i){
	  	cout<<"--Cloud "<<i+1<<"";
	  }
	  cout<<"\n";
	  for (int i = 0; i < b_count; ++i){
	    	cout<<"Broker "<<i+1<<" |";
	    	for (int j = 0; j <c_count; ++j){
	    		std::cout << std::setw(8);
			    	cout<<vm_alloted_to_broker[i][j];
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
    std::ifstream in("input1.txt");
  
    in>>c_count;
      in>>b_count;
    
    vector<vector<float>> price_matrix(b_count);

   
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
    		price_matrix[i].push_back(temp);

    	}
    }
    in.close();

    //''''''''''read input from file ends

	std::vector<int> copy_broker_demand=broker_demand;
	cout<<"price offered by cloud to brokers:\n\n         ";


	for (int i = 0; i < c_count; ++i){
			cout<<"|Cloud "<<i+1<<"    ";
	}
	cout<<"\n";

	for (int i = 0; i < b_count; ++i){
			    	cout<<"Broker "<<i+1<<" |";
			    	for (int j = 0; j <c_count; ++j)
			    	{
			    		cout<<"    "<<price_matrix[i][j]<<"      |";
			    		
			    	}
			    	cout<<"\n";
	}

	std::vector<int> broker_max_price{8,12,15,7,23};

	cout<<"\n\nMax price at which broker can buy:\n";
	    for (int i = 0; i < b_count; ++i) {
	    	cout<<"Broker "<<i+1<<" :"<<broker_max_price[i]<<"\n";
	    }

	cout<<"\n\nbroker demand::\n";
		for (int i = 0; i < b_count; ++i) {
			cout<<"Broker "<<i+1<<" :"<<broker_demand[i]<<"\n";
		}

	cout<<"\ncloud supply::\n";
	    for (int i = 0; i < c_count; ++i) {
	    	cout<<"cloud "<<i+1<<" :"<<cloud_supply[i]<<"\n";
	    }
	    
	vector<std::vector<float>> broker_preference;
	broker_preference=price_matrix;
	vector<std::vector<float>> c_price_matrix=price_matrix;
	   

	//creating broker preferencr list    
	for (int i = 0; i <b_count; ++i){
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

	for (int i = 0; i < b_count; ++i){
		    	cout<<"Broker"<<i+1<<" : ";
		    	for (int j = 0; j <c_count; ++j){
		    		cout<<"c"<<broker_preference[i][j]+1<<" ";
		    	}
		    	cout<<"\n";
	}

		//creating cloud  preferencr list    
	    vector<vector<float>> transpose_price_matrix(c_count);

	    	for (int i = 0; i < c_count; ++i)	
	    		for (int j = 0; j < b_count; ++j)
	    			transpose_price_matrix[i].push_back(price_matrix[j][i]);
	    		
		vector<std::vector<float>> cloud_preference;
		cloud_preference=transpose_price_matrix;
		 c_price_matrix=transpose_price_matrix;

	for (int i = 0; i <c_count; ++i){
	    	/* code */
	    	sort(cloud_preference[i].begin(), cloud_preference[i].end()); 
	    	reverse(cloud_preference[i].begin(), cloud_preference[i].end());
	    	for (int j = 0; j < b_count; ++j)
	    	{
	    	    for (int k = 0; k < b_count; ++k)
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
		    		cout<<"B"<<cloud_preference[i][j]+1<<" ";
		    	}
		    	cout<<"\n";
	}


	std::vector<int> br_current_pref(b_count,0);

	std::vector<int> residual_vms_cloud=cloud_supply;
	vector<vector<int>> request_list(b_count);


	vector<vector<int>> vm_alloted_to_broker( b_count , vector<int> (c_count, 0));

	int choice2;
    cout<<"1.broker making request \n 2.cloud making request";
    cin>>choice2;
    if(choice2==1){

    	//allotment of vm to broker by cloud provider in each iteration
		for(int i = 0; i <c_count+b_count; ++i){    
		  // saving broker request in each iteration
		  int no_request=0;
		  for (int br = 0; br < b_count; ++br){
		      	
	              if(broker_demand[br]>0){
		      		no_request++;
		      		//	cout<<"\nbroker :"<<br+1<<" demand :"<<broker_demand[br];
		      	 	int cloud_id=broker_preference[br][br_current_pref[br]];
					//cout<<"cloudid:"<<cloud_id+1<<" ";
			        br_current_pref[br]++;
			        // if( br_current_pref[br]+1==c_count)
			        // 	br_current_pref[br]=br_current_pref[br]-1;
			      	request_list[cloud_id].push_back(br);
		      		}
		  }

		  if(no_request==0)
		    	{
		    		cout<<"\n\n******ALL BROKER DEMAND MET WITH STABLE ALLOTMENT********\n\nfinal allotment\n";

		    		show_vm_allotments(vm_alloted_to_broker,c_count,b_count,broker_demand);
		    		break;}

		  cout<<"\n------------------------------------------------\niteration:"<<i+1<<"\n";


			//sort the request list
		  for (int i = 0; i < c_count; ++i){ 
		      for(int j = 0; j < request_list[i].size(); ++j){

		      	  int max=request_list[i][j]; //broker id
		      	  //cout<<"\nmax:"<<max;
		      	  //int max=0;
			      for (int k = j+1; k <request_list[i].size() ; ++k){
		      			if(price_matrix[max][i]<price_matrix[request_list[i][k]][i]){	
		      					//swap
		      					request_list[i][j]=request_list[i][k];
		      					request_list[i][k]=max;
		      					max=request_list[i][j];
		      			}
			      }
		      }   	
		  }
 		
		  cout<<"\n\nRequest list sorted as per cloud preference:\n ";

		      for (int i = 0; i < c_count; ++i){ 
		      	cout<<"\ncloud "<<i+1<<": ";
		      	for (int j = 0; j < request_list[i].size(); ++j){
		      		cout<<"B"<<request_list[i][j]+1<<" ";
		      	}   	
		      }


	        //allotment
			      
		  for (int i = 0; i < c_count; ++i){
		      		residual_vms_cloud[i]=cloud_supply[i];

		      		 //(taking back previous allotment and reassigning again)
		      		for(int m = 0; m < request_list[i].size(); ++m){
		      			broker_demand[request_list[i][m]]+=vm_alloted_to_broker[request_list[i][m]][i];
		      			vm_alloted_to_broker[request_list[i][m]][i]=0;
		      		}	      			
	               //  cout<<"\ncloud "<<i+1<<"  allotment\n";
		      		for (int j = 0; j < request_list[i].size(); ++j){
	                     
		      		if(residual_vms_cloud[i]>0 ){
		      			
		      			int demand=broker_demand[request_list[i][j]];
		      			//cout<<"\nB:"<<request_list[i][j]+1<<" demand:"<<demand<<"\n";
		      			if(demand<residual_vms_cloud[i])
			      		{
		
			      			residual_vms_cloud[i]-=demand;			      
			      			broker_demand[request_list[i][j]]=0;
			      			vm_alloted_to_broker[request_list[i][j]][i]=demand;
			      		}
			      		else
			      		{  
			      			
			      				broker_demand[request_list[i][j]]=demand+vm_alloted_to_broker[request_list[i][j]][i]-residual_vms_cloud[i];
				      			vm_alloted_to_broker[request_list[i][j]][i]=residual_vms_cloud[i];
				      							      			
				      			broker_demand[request_list[i][j]]=demand-residual_vms_cloud[i];
				      		//	cout<<"\n remianing request for broker "<<request_list[i][j]+1<<":"<<broker_demand[request_list[i][j]]<<"\n";
				      			residual_vms_cloud[i]=0;
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
	      cout<<"Broker demand after "<<i<<" allotment:\n";
		  for (int i = 0; i < b_count; ++i){
					cout<<"  B"<<i+1<<":"<<broker_demand[i];
		  }
		}

    }
    else{

    	std::vector<int> cloud_current_pref(c_count,0);
		std::vector<int> pending_demand_br=broker_demand;
		vector<vector<int>> request_list(b_count);
		//	vector<vector<int>> vm_alloted_to_broker( c_count , vector<int> (b_count, 0));

    	for(int i = 0; i <b_count+c_count; ++i){    
		  // saving broker request in each iteration
		  

     		int all_Demand=0;
     		int no_request=0;
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

		  if(all_Demand==0 or  no_request==0)
		    	{
		    		cout<<"\n\n******ALL BROKER DEMAND MET WITH STABLE ALLOTMENT********\n\nfinal allotment\n";

		    		show_vm_allotments(vm_alloted_to_broker,c_count,b_count,broker_demand);
		    		break;}

		  cout<<"\n------------------------------------------------\niteration:"<<i+1<<"\n";


			//sort the request list
		  for (int i = 0; i < b_count; ++i){ 
		      for(int j = 0; j < request_list[i].size(); ++j){

		      	  int min=request_list[i][j]; //broker id
		      	  //cout<<"\nmax:"<<max;
		      	  //int max=0;
			      for (int k = j+1; k <request_list[i].size() ; ++k){
		      			if(transpose_price_matrix[min][i]>transpose_price_matrix[request_list[i][k]][i]){	
		      					//swap
		      					request_list[i][j]=request_list[i][k];
		      					request_list[i][k]=min;
		      					min=request_list[i][j];
		      			}
			      }
		      }   	
		  }

		  cout<<"\n\nRequest list sorted as per broker preference:\n ";

		      for (int i = 0; i < b_count; ++i){ 
		      	cout<<"\nBroker "<<i+1<<": ";
		      	for (int j = 0; j < request_list[i].size(); ++j){
		      		cout<<"C"<<request_list[i][j]+1<<" ";
		      	}   	
		      }


	        //broker checking request and accepting cloud preference based on his priority
			      
		  for (int i = 0; i < b_count; ++i){
		      		pending_demand_br[i]=broker_demand[i];

		      		
		      		// cout<<"\n\nbroker "<<i+1<<"  allotment\n";

	      		      
		      		 

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
		      			//cout<<"\nC"<<request_list[i][j]+1<<" supply is :"<<supply<<"\n";
		      			if(supply<pending_demand_br[i])
			      		{
		
			      			pending_demand_br[i]-=supply;			      
			      			cloud_supply[request_list[i][j]]=0;
			      			vm_alloted_to_broker[i][request_list[i][j]]=supply;
			      		//	cout<<"\n remianing request for broker is "<<i+1<<" is:"<<pending_demand_br[i];
			      		}
			      		else
			      		{  
			      			
			      				//broker_demand[request_list[i][j]]=supply+vm_alloted_to_broker[request_list[i][j]][i]-pending_demand_br[i];
				      			vm_alloted_to_broker[i][request_list[i][j]]=pending_demand_br[i];
				      							      			
				      			cloud_supply[request_list[i][j]]=supply-pending_demand_br[i];
				      		//	cout<<"\n remianing request for broker "<<request_list[i][j]+1<<":0\n";
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
		  for (int i = 0; i < c_count; ++i){
					cout<<"  C"<<i+1<<":"<<cloud_supply[i];
		  }
		}


    }

	//utility calcution for cloud

	//using broker prederence and vm allotment
	int broker_utility[b_count];
   memset(broker_utility, 0, sizeof(broker_utility));
   cout<<"\n";
      for (int i = 0; i < b_count; ++i)
      {
      //	cout<<i+1<<" broker\n";
      	for (int j = 0; j < c_count; ++j)
      	{
      		auto itr = find(broker_preference[i].begin(),broker_preference[i].end() , j);
      		int index = itr -broker_preference[i].begin();
      		
      		int l=index+1;    //cloud rank in broker prefrence
      		
      		broker_utility[i]+=(c_count-l+1)*vm_alloted_to_broker[i][j];

      	}
      	cout<<endl<<endl;
      	/* code */
      }

      int cloud_utility[c_count];
   memset(cloud_utility, 0, sizeof(cloud_utility));
   cout<<"\n";
      for (int i = 0; i < c_count; ++i)
      {
      	//	cout<<i+1<<" cloud \n";
      	for (int j = 0; j < b_count; ++j)
      	{
      		auto itr = find(cloud_preference[i].begin(),cloud_preference[i].end() , j);
      		int index = itr -cloud_preference[i].begin();
      		//cout<<j+1<<" broker "<<index+1<<" \n";
      		int l=index+1;    //cloud rank in broker prefrence
      		cloud_utility[i]+=(b_count-l+1)*vm_alloted_to_broker[j][i];

      	}
      
      	/* code */
      }

if(choice2==1)
 cout<<"  Utility when BROKER made request\n"; 
else
 cout<<"  Utility when CLOUD made request\n"; 	

cout<<"\n  Broker Utility\n";
cout<<"---------------------\n";	
      for (int i = 0; i < b_count; ++i)
      {
      	cout<<"  Broker "<<i+1<<" : "<<broker_utility[i]<<"\n";
      }
cout<<"\n  Cloud  Utility\n" ;   
cout<<"---------------------\n";  
      for (int i = 0; i < c_count; ++i)
      {
      	cout<<"  Cloud "<<i+1<<" : "<<cloud_utility[i]<<"\n";
      }

      //utility calcution for broker



	    return 0;
}