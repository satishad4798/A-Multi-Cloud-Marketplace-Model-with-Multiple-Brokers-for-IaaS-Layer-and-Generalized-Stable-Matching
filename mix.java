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
					    for (int i = 0; i < b_count; ++i)
					    {
					    	 System.out.print("Broker "+(i+1)+" |");
					    	for (int j = 0; j <c_count; ++j)
					    	{
					    		 System.out.print("    "+vm_alloted_to_broker[i][j]+"      |");
					    		
					    	}
					    	 System.out.print("\n");
					    }  
}
    	
        public static void main(String args[]){  



            int b_count,c_count;
            // std::vector<int> cloud_supply;
            // std::vector<int> broker_demand;
            int[] broker_demand ={35,30,23,12};
            int[] cloud_supply = {10,20,32,38};
            // cloud_supply={10,20,32,38};
            // broker_demand={35,30,23,12};

            // vector<vector<int>> price_matrix{{5,10,7,5},{3,13,8,7},{7,12,9,9},{8,15,5,8}};
            int[][] price_matrix = {{5,7,7,5},{3,6,8,7},{7,8,9,9},{8,10,5,8}};
           // int[][] price_matrix = {{5,3,7,8,6},{10,13,12,15,12},{7,8,9,5,10},{5,7,9,8,10}};
       
            Scanner sc= new Scanner(System.in); //System.in is a standard input stream.
           System.out.println("1.default valus 2.manual inputs");
           int choice= sc.nextInt();
                if(choice==2)
                {
                    System.out.println("no of broker,no of cloud \n hint:copy paste input.txt"); 
                    b_count= sc.nextInt();
                    c_count= sc.nextInt();
                    System.out.println("cloud supply array"); 
                    for(int i=0;i<c_count;i++)
                    cloud_supply[i]= sc.nextInt();

                    System.out.println("broker supply array"); 
                    for(int i=0;i<b_count;i++)
                    broker_demand[i]= sc.nextInt();

                    System.out.println("price matrix : rows are broker cloumns are cloud \n hint:copy paste input.txt"); 
                    for(int i=0;i<b_count;i++)
                    for(int j=0;j<c_count;j++)
                    price_matrix[j][i]= sc.nextInt();

                    sc.close();

                }
                
            
            System.out.println("price offered by cloud to brokers");
            b_count=broker_demand.length;
             c_count=cloud_supply.length;
            System.out.print("         ");
            for (int i = 0; i < b_count; ++i){
                System.out.print("|Broker "+(i+1)+"    ");
            }
            System.out.print("\n");
            for (int i = 0; i < c_count; ++i){
                    System.out.print("Cloud "+i+" |");
                    for (int j = 0; j <b_count; ++j)
                    {
                        System.out.print("    "+price_matrix[i][j]+"      |");
                        
                    }
                    System.out.print("\n");
                }

                //break1

            int[] broker_max_price={8,12,15,7,12};

            System.out.print("\n\n price at which broker want buy:\n");
            for (int i = 0; i < b_count; ++i) {
                    System.out.print("Broker "+(i+1)+" :"+broker_max_price[i]+"\n");
                }

            System.out.print("\n\nbroker demand::\n");

                for (int i = 0; i < b_count; ++i) {
                    System.out.print("Broker "+(i+1)+" :"+broker_demand[i]+"\n");
                }

           System.out.print("\ncloud supply::\n");

                for (int i = 0; i < c_count; ++i) {
                    System.out.print("cloud "+(i+1)+" :"+cloud_supply[i]+"\n");
                }
            
                int[][] cloud_preference=new int[c_count][b_count];
                int[][] c_price_matrix=new int[c_count][b_count];

                for (int i = 0; i < c_count; ++i) {
                    for(int j=0;j<b_count;j++){
                        cloud_preference[i][j]=price_matrix[i][j];
                        c_price_matrix[i][j]=price_matrix[i][j];
                    }}
                
                //creating cloud preferencr list 
                
                for (int i = 0; i <c_count; ++i)
                {
                    /* code */
                     Arrays.sort(cloud_preference[i]);
                     for (int xx = 0; xx < b_count / 2; xx++) {
                        int t = cloud_preference[i][xx];
                        cloud_preference[i][xx] = cloud_preference[i][b_count - xx - 1];
                        cloud_preference[i][b_count - xx - 1] = t;
                    }
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
                System.out.print("\ncloud preference based on price\n");
            for (int i = 0; i < c_count; ++i){

                    System.out.print("cloud"+(i+1)+" : ");
                for (int j = 0; j <b_count; ++j){
                        System.out.print("B"+(cloud_preference[i][j]+1)+" ");
                }
                    System.out.print("\n");
            }
            
           
           int cloud_current_pref[] = new int[c_count]; 
         

            int [] pending_demand_br=new int[b_count];
            for(int x=0;x<broker_demand.length;x++)
            pending_demand_br[x]=broker_demand[x];


            //vector<vector<int>> request_list(c_count);
          //  ArrayList<Integer> request_list = new ArrayList<>(c_count);
            ArrayList<ArrayList<Integer> > request_list = new ArrayList<ArrayList<Integer> >(b_count);
            int[][] vm_alloted_to_broker=new int[c_count][b_count];
            
            for (int y = 0; y <(c_count+b_count); ++y){
                    
                        int all_Demand=0;
                        int no_request=0;
                    for (int x = 0; x < b_count; ++x)
                    {
                        all_Demand+=pending_demand_br[x];
                    }    
                    // saving broker request in each iteration
                    for (int cl = 0; cl < c_count; ++cl)
                    {
                        request_list.add(new ArrayList<Integer>());
                    }
                    for (int cl = 0; cl < c_count; ++cl){
                       
                        System.out.print("\nbroker :"+(cl+1) +" demand :"+pending_demand_br[cl]);

                        if(cloud_supply[cl]>0)
                        {
                            no_request++;
                            int broker_id=cloud_preference[cl][cloud_current_pref[cl]];
                            // System.out.print+"cloudid:"+broker_id+1+" ";
                            cloud_current_pref[cl]++;
                            if(cloud_current_pref[cl]>c_count-1)
                            cloud_current_pref[cl]=c_count-1;
                            // request_list.get(0).add(0,12);
                           request_list.get(broker_id).add(cl);
                        }

                    }
                    if(all_Demand==0 || no_request==0 ){
                            System.out.print("\n\n******ALL BROKER DEMAND MET WITH STABLE ALLOTMENT********\n\nfinal allotment\n");
            
                            show_vm_allotments(vm_alloted_to_broker,c_count,b_count,broker_demand);
                            break;
                    }
            
                    System.out.print("\n------------------------------------------------\niteration:"+(y+1)+"\n");
            
                    //broker_demand=copy_broker_demand;
                    //sort the request list
                    for (int ii = 0; ii < c_count; ++ii){      
                            for (int j = 0; j < request_list.get(ii).size() ; ++j){
                                int min=request_list.get(ii).get(j); //broker id
                                // System.out.print+"\nmax:"+min;
                                //int min=0;
                                for (int k = j+1; k <request_list.get(ii).size();  ++k){
                                    if(price_matrix[min][ii]>price_matrix[request_list.get(ii).get(k)][ii])
                                        {
                                            
                                            //swap
                                            request_list.get(ii).set(j,request_list.get(ii).get(k));

                                            // System.out.print+"\nSatish1:"+request_list[ii][j];
                                            request_list.get(ii).set(k,min);
                                           
                                        //	 System.out.print+"\nSatish2: "+request_list[ii][k];
                                            min=request_list.get(ii).get(j);

                                        }
                                }
                            }   
                    }
            
                    System.out.print("\n\nRequest list sorted as per borker preference:\n ");
            
                    for (int x = 0; x < b_count; ++x){  
                        System.out.print("\nBroker "+(x+1)+": ");
                            for (int j = 0; j < request_list.get(x).size(); ++j)
                            {
                                System.out.print("C"+(request_list.get(x).get(j)+1)+" ");
                            }
                            
                        }
            

                   
                  
                    for (int i = 0; i < c_count; ++i){
                        
                       
                        pending_demand_br[i]=broker_demand[i];
                        //System.out.print("\nsupply:\n"+broker_demand[i]);

                            //(taking back previous allotment and reassigning again)
                            for (int m = 0; m < request_list.get(i).size(); ++m){
                                cloud_supply[request_list.get(i).get(m)]+=vm_alloted_to_broker[i][request_list.get(i).get(m)];
                                vm_alloted_to_broker[i][request_list.get(i).get(m)]=0;
                            }
                            

                            System.out.print("\nbroker  "+(i+1)+"  allotment\n");
                            for (int j = 0; j < request_list.get(i).size(); ++j)
                            {
                            //     System.out.print("\nsupply:\n"+pending_demand_br[i]);
                                if(pending_demand_br[i]>0 ){
                                   //  System.out.print("\ninside if condtion\n");

                                    int supply=cloud_supply[request_list.get(i).get(j)];
                                      //  System.out.print("\nB:"+(request_list.get(i).get(j)+1)+" supply:"+supply+"\n");
                                    if(supply<pending_demand_br[i])
                                    {
                                         //   System.out.print("\n remianing request for broker :"+(request_list.get(i).get(j)+1)+"0\n");
                                        

                                        pending_demand_br[i]-=supply;
                                        //System.out.print+"\nresidual_vms_cloud[i]"+pending_demand_br[i];

                                        // update cloud demand if vm was rejected by broker  (if doubt ask)
                                        
                                        // broker_demand[request_list.get(i).get(j)]=supply+vm_alloted_to_broker[request_list.get(i).get(j)][i]-present_allotment;   //previous allotment and present allotment
                                        cloud_supply[request_list.get(i).get(j)]=0;

                                        // vm_alloted_to_broker[request_list.get(i).get(i)][j]=vm_alloted_to_broker[request_list.get(i).get(i)][j]+supply;
                                         vm_alloted_to_broker[i][request_list.get(i).get(j)]=supply;
                                    }

                                    else
                                    {   //allot only if not alloted
                                       // System.out.print("\ninside else condtion\n");
                                           // broker_demand[request_list.get(i).get(j)]=supply+vm_alloted_to_broker[request_list.get(i).get(j)][i]-pending_demand_br[i];
                                            vm_alloted_to_broker[i][request_list.get(i).get(j)]=pending_demand_br[i];
                                            
                                            // System.out.print+"\nsatish"+supply-pending_demand_br[i];
                                            cloud_supply[request_list.get(i).get(j)]=supply-pending_demand_br[i];
                                          //      System.out.print("\n remianing request for broker "+request_list.get(i).get(j)+1+":"+broker_demand[request_list.get(i).get(j)]+"\n");
                
                                            pending_demand_br[i]=0;
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

                    for (int x = 0; x < c_count; ++x){
                            System.out.print("  C"+(x+1)+":"+cloud_supply[x]);
                    }
            }    
        }                                
    }  