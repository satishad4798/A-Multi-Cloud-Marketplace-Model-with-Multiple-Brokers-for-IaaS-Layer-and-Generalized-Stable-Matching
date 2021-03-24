import java.util.*;

    class Simple{  

        static void  show_vm_allotments(int[][] vm_alloted_to_broker,int c_count,int b_count,int[] broker_demand)
{
	    System.out.print("        ");
					  for (int i = 0; i < c_count; ++i)
					  {
					  	 System.out.print("|Cloud "+(i+1)+"    ");
					  }
					   System.out.print("\n");
					    for (int i = 0; i < c_count; ++i)
					    {
					    	 System.out.print("Broker "+(i+1)+" |");
					    	for (int j = 0; j <b_count; ++j)
					    	{
					    		 System.out.print("    "+vm_alloted_to_broker[i][j]+"      |");
					    		
					    	}
					    	 System.out.print("\n");
					    }  
}
    	
        public static void main(String args[]){  




            // std::vector<int> cloud_supply;
            // std::vector<int> broker_demand;
            int[] broker_demand ={35,30,23,12};
            int[] cloud_supply = {10,20,32,38};
            // cloud_supply={10,20,32,38};
            // broker_demand={35,30,23,12};

            // vector<vector<int>> price_matrix{{5,10,7,5},{3,13,8,7},{7,12,9,9},{8,15,5,8}};
            int[][] price_matrix = {{5,10,7,5},{3,13,8,7},{7,12,9,9},{8,15,5,8}};
        

                
            
            System.out.println("price offered by cloud to brokers");
            int b_count=broker_demand.length;
            int  c_count=cloud_supply.length;
            System.out.print("         ");
            for (int i = 0; i < c_count; ++i){
                System.out.print("|Cloud "+(i+1)+"    ");
            }
            System.out.print("\n");
            for (int i = 0; i < c_count; ++i){
                    System.out.print("Broker "+i+" |");
                    for (int j = 0; j <b_count; ++j)
                    {
                        System.out.print("    "+price_matrix[i][j]+"      |");
                        
                    }
                    System.out.print("\n");
                }

                //break1

            int[] broker_max_price={8,12,15,7};

            System.out.print("\n\nMax price at which broker can buy:\n");
            for (int i = 0; i < b_count; ++i) {
                    System.out.print("Broker "+(i+1)+" :"+broker_max_price[i]+"\n");
                }

            System.out.print("\n\nbroker demand::\n");

                for (int i = 0; i < b_count; ++i) {
                    System.out.print("Broker "+(i+1)+" :"+broker_demand[i]+"\n");
                }

           System.out.print("\ncloud supply::\n");

                for (int i = 0; i < b_count; ++i) {
                    System.out.print("cloud "+(i+1)+" :"+cloud_supply[i]+"\n");
                }
            
                int[][] broker_preference=new int[b_count][c_count];
                int[][] c_price_matrix=new int[b_count][c_count];

                for (int i = 0; i < b_count; ++i) {
                    for(int j=0;j<c_count;j++){
                        broker_preference[i][j]=price_matrix[i][j];
                        c_price_matrix[i][j]=price_matrix[i][j];
                    }}
                
                // broker preferencr list
                
                for (int i = 0; i <b_count; ++i)
                {
                    /* code */
                    Arrays.sort(broker_preference[i]);
                   
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
                System.out.print("\nbroker preference based on price\n");
            for (int i = 0; i < c_count; ++i){

                    System.out.print("Broker"+(i+1)+" : ");
                for (int j = 0; j <b_count; ++j){
                        System.out.print("c"+(broker_preference[i][j]+1)+" ");
                }
                    System.out.print("\n");
            }
            
           
           int br_current_pref[] = new int[b_count]; 
         

            int [] residual_vms_cloud=new int[c_count];
            for(int x=0;x<cloud_supply.length;x++)
            residual_vms_cloud[x]=cloud_supply[x];


            //vector<vector<int>> request_list(c_count);
          //  ArrayList<Integer> request_list = new ArrayList<>(c_count);
            ArrayList<ArrayList<Integer> > request_list = new ArrayList<ArrayList<Integer> >(c_count);
            int[][] vm_alloted_to_broker=new int[b_count][c_count];
            
            for (int y = 0; y <(c_count+b_count); ++y){
                    
                     int no_request=0;    
                    // saving broker request in each iteration

                    for (int br = 0; br < b_count; ++br){
                       request_list.add(new ArrayList<Integer>());
                        
                        if(broker_demand[br]>0)
                        {
                            no_request++;
                           // System.out.print("\nbroker :"+(br+1) +" demand :"+broker_demand[br]);
                            int cloud_id=broker_preference[br][br_current_pref[br]];
                            // System.out.print+"cloudid:"+cloud_id+1+" ";
                            br_current_pref[br]++;
                            if(br_current_pref[br]>c_count-1)
                            br_current_pref[br]=c_count-1;
                            // request_list.get(0).add(0,12);
                            request_list.get(cloud_id).add(br);
                        }

                    }
                    if(no_request==0){
                            System.out.print("\n\n******ALL BROKER DEMAND MET WITH STABLE ALLOTMENT********\n\nfinal allotment\n");
            
                            show_vm_allotments(vm_alloted_to_broker,c_count,b_count,broker_demand);
                            break;
                    }
            
                    System.out.print("\n------------------------------------------------\niteration:"+(y+1)+"\n");
            
                    //broker_demand=copy_broker_demand;
                    //sort the request list
                    for (int ii = 0; ii < c_count; ++ii){      
                            for (int j = 0; j < request_list.get(ii).size() ; ++j){
                                int max=request_list.get(ii).get(j); //broker id
                                // System.out.print+"\nmax:"+max;
                                //int max=0;
                                for (int k = j+1; k <request_list.get(ii).size();  ++k){
                                    if(price_matrix[max][ii]<price_matrix[request_list.get(ii).get(k)][ii])
                                        {
                                            
                                            //swap
                                            request_list.get(ii).set(j,request_list.get(ii).get(k));

                                            // System.out.print+"\nSatish1:"+request_list[ii][j];
                                            request_list.get(ii).set(k,max);
                                           
                                        //	 System.out.print+"\nSatish2: "+request_list[ii][k];
                                            max=request_list.get(ii).get(j);

                                        }
                                }
                            }   
                    }
            
                    System.out.print("\n\nRequest list sorted as per cloud preference:\n ");
            
                    for (int x = 0; x < c_count; ++x){  System.out.print("\ncloud "+(x+1)+": ");
                            for (int j = 0; j < request_list.get(x).size(); ++j)
                            {
                                System.out.print("B"+(request_list.get(x).get(j)+1)+" ");
                            }
                            
                        }
            

                   
                  
                    for (int i = 0; i < c_count; ++i){
                        
                       
                        residual_vms_cloud[i]=cloud_supply[i];
                        //System.out.print("\nsupply:\n"+cloud_supply[i]);

                            //(taking back previous allotment and reassigning again)
                            for (int m = 0; m < request_list.get(i).size(); ++m){
                                broker_demand[request_list.get(i).get(m)]+=vm_alloted_to_broker[request_list.get(i).get(m)][i];
                                vm_alloted_to_broker[request_list.get(i).get(m)][i]=0;
                            }
                            

                            System.out.print("\ncloud "+(i+1)+"  allotment\n");
                            for (int j = 0; j < request_list.get(i).size(); ++j)
                            {
                            //     System.out.print("\nsupply:\n"+residual_vms_cloud[i]);
                                if(residual_vms_cloud[i]>0 ){
                                   //  System.out.print("\ninside if condtion\n");

                                    int demand=broker_demand[request_list.get(i).get(j)];
                                      //  System.out.print("\nB:"+(request_list.get(i).get(j)+1)+" demand:"+demand+"\n");
                                    if(demand<residual_vms_cloud[i])
                                    {
                                         //   System.out.print("\n remianing request for broker :"+(request_list.get(i).get(j)+1)+"0\n");
                                        

                                        residual_vms_cloud[i]-=demand;
                                        //System.out.print+"\nresidual_vms_cloud[i]"+residual_vms_cloud[i];

                                        // update broker demand if vm was taken back (if doubt ask)
                                        
                                        // broker_demand[request_list.get(i).get(j)]=demand+vm_alloted_to_broker[request_list.get(i).get(j)][i]-present_allotment;   //previous allotment and present allotment
                                        broker_demand[request_list.get(i).get(j)]=0;

                                        vm_alloted_to_broker[request_list.get(i).get(j)][i]=vm_alloted_to_broker[request_list.get(i).get(j)][i]+demand;
                                    }
                                    else
                                    {   //allot only if not alloted
                                       // System.out.print("\ninside else condtion\n");
                                            broker_demand[request_list.get(i).get(j)]=demand+vm_alloted_to_broker[request_list.get(i).get(j)][i]-residual_vms_cloud[i];
                                            vm_alloted_to_broker[request_list.get(i).get(j)][i]=residual_vms_cloud[i];
                                            
                                            // System.out.print+"\nsatish"+demand-residual_vms_cloud[i];
                                            broker_demand[request_list.get(i).get(j)]=demand-residual_vms_cloud[i];
                                          //      System.out.print("\n remianing request for broker "+request_list.get(i).get(j)+1+":"+broker_demand[request_list.get(i).get(j)]+"\n");
                
                                            residual_vms_cloud[i]=0;
                                    }

                                }
                                else 
                                    {

                                        request_list.get(i).size();
                                        request_list.get(i).remove(j); //if error comment this @47

                                       // request_list.get(i).erase(request_list.get(i).begin() + j, request_list.get(i).end());
                                        break;
                                    }
                            }
                    
                    }

                    System.out.print("\n vms alloted in "+(y+1)+" :iteration\n\n");
                   show_vm_allotments(vm_alloted_to_broker,c_count,b_count,broker_demand);
                  

                   System.out.print("Broker demand after "+y+" allotment:\n");

                    for (int x = 0; x < b_count; ++x){
                            System.out.print("  B"+(x+1)+":"+broker_demand[x]);
                    }

            }                    

                System.out.print("Hello Java");  
                }  
    }  