    import java.util.*;
    import java.util.List;

        class Stable_Matching{  

            static void  show_vm_allotments(int[][] vm_alloted_to_broker,int c_count,int b_count,int[] broker_demand){
            System.out.print("        ");
                        for (int i = 0; i < c_count; ++i){
                            System.out.print("|Cloud "+(i+1)+"    ");
                        }
                        System.out.print("\n");
                            for (int i = 0; i < b_count; ++i){
                                System.out.print("Broker "+(i+1)+" |");
                                for (int j = 0; j <c_count; ++j) {
                                    System.out.print("    "+vm_alloted_to_broker[i][j]+"      |");     
                                }
                                System.out.print("\n");
                            }  
            }
            
            public static void main(String args[]){  

                int b_count,c_count;
                int[] broker_demand;
                int[] cloud_supply;
                int[][] price_matrix;
            
                Scanner sc= new Scanner(System.in); //System.in is a standard input stream.
            System.out.println("\n\nEnter your choice:\n1.default values\n2.manual inputs");
            int choice= sc.nextInt();
            
                    if(choice==1){
                        
                        broker_demand=new int[]{35,30,23,12,};
                        cloud_supply=new int[] {10,20,32,38};
                        price_matrix =new int[][] {{5,10,7,5},{3,13,8,7},{7,12,9,9},{8,15,5,8}};
                    
                    }
                    else{    
                    
                        System.out.println("no of broker,no of cloud \n hint:copy paste input.txt"); 
                    
                        c_count= sc.nextInt();
                        b_count= sc.nextInt();
                        broker_demand=new int[b_count];
                        cloud_supply=new int[c_count];
                        price_matrix=new int[b_count][c_count];
                        System.out.println("cloud supply array"); 
                        for(int i=0;i<c_count;i++)
                        cloud_supply[i]= sc.nextInt();

                        System.out.println("broker supply array"); 
                        for(int i=0;i<b_count;i++)
                        broker_demand[i]= sc.nextInt();

                        System.out.println("price matrix : rows are broker cloumns are cloud \n hint:copy paste input.txt"); 
                        for(int i=0;i<b_count;i++)
                        for(int j=0;j<c_count;j++){ 
                        price_matrix[i][j]= sc.nextInt();
                        }
                    }
                    
                
                    b_count=broker_demand.length;
                    c_count=cloud_supply.length;

                int[][] transpose_price_matrix=new int[c_count][b_count];
                for(int i=0;i<c_count;i++)
                for(int j=0;j<b_count;j++)
                    transpose_price_matrix[i][j]=price_matrix[j][i];

                System.out.println("price offered by cloud to brokers");
            
                System.out.print("         ");
                for (int i = 0; i < c_count; ++i){
                    System.out.print("|Cloud "+(i+1)+"    ");
                }
                System.out.print("\n");
                for (int i = 0; i < b_count; ++i){
                        System.out.print("Broker "+i+" |");
                        for (int j = 0; j <c_count; ++j)
                        {
                            System.out.print("    "+price_matrix[i][j]+"      |");
                            
                        }
                        System.out.print("\n");
                    }

                    //break1

                int[] broker_max_price={8,12,15,7,12};

                System.out.print("\n\n price at which broker want buy:\n");
                for (int i = 0; i < broker_max_price.length; ++i) {
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
                    int[][] c_transpose=new int[c_count][b_count];
                
    
                    for (int i = 0; i < c_count; ++i) {
                        for(int j=0;j<b_count;j++){
                            cloud_preference[i][j]=transpose_price_matrix[i][j];
                            c_transpose[i][j]=transpose_price_matrix[i][j];
                            
                        } 
                    }
    
                //creating cloud preferencr list 
                    
                    for (int i = 0; i <c_count; ++i){
                        /* code */
                        Arrays.sort(cloud_preference[i]);
                        for (int xx = 0; xx < b_count / 2; xx++){
                            int t = cloud_preference[i][xx];
                            cloud_preference[i][xx] = cloud_preference[i][b_count - xx - 1];
                            cloud_preference[i][b_count - xx - 1] = t;
                        }

                        for (int m = 0; m < c_count; ++m){
                            for(int j=0;j<b_count;j++){
                                System.out.print(cloud_preference[m][j]+" ");
                            }System.out.print("\n");}

                        for (int j = 0; j < b_count; ++j){
                            for (int k = 0; k < b_count; ++k){
                                if(cloud_preference[i][j]==c_transpose[i][k])
                                    {
                                        cloud_preference[i][j]=k;
                                        c_transpose[i][k]=0;
                                        break;
                                    }
                            }
                        }  
                    }

                // prining broker preference
                    System.out.print("\ncloud preference based on price\n");
                for(int i = 0; i < c_count; ++i){

                        System.out.print("cloud"+(i+1)+" : ");
                    for (int j = 0; j <b_count; ++j){
                            System.out.print("B"+(cloud_preference[i][j]+1)+" ");
                    }
                        System.out.print("\n");
                }

            
                    // broker preferencr list
                    int[][] broker_preference=new int[b_count][c_count];
                    int[][] c_price_matrix=new int[b_count][c_count];
                    for (int i = 0; i < b_count; ++i){ //47
                        for(int j=0;j<c_count;j++){ //47
                            broker_preference[i][j]=price_matrix[i][j];
                            c_price_matrix[i][j]=price_matrix[i][j];
                        }}
                    for (int i = 0; i <b_count; ++i){
                        Arrays.sort(broker_preference[i]);                 
                        for (int j = 0; j < c_count; ++j){
                            for (int k = 0; k < c_count; ++k){
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
                for (int i = 0; i < b_count; ++i){
    
                        System.out.print("Broker"+(i+1)+" : ");
                    for (int j = 0; j <c_count; ++j){
                            System.out.print("c"+(broker_preference[i][j]+1)+" ");
                    }
                        System.out.print("\n");
                }
                
 
                int[][] vm_alloted_to_broker=new int[b_count][c_count];
                
                System.out.println("\nEnter your choice\n 1.broker proposing the request\n 2.cloud proposing the request");
                int choice2= sc.nextInt();
                sc.close();
                //if broker making request
                if(choice2==1){
                    int br_current_pref[] = new int[b_count]; 
                    int [] residual_vms_cloud=new int[c_count];
                    for(int x=0;x<cloud_supply.length;x++)
                    residual_vms_cloud[x]=cloud_supply[x];
        

                    ArrayList<ArrayList<Integer> > request_list = new ArrayList<ArrayList<Integer> >(c_count); //47
                    //int[][] vm_alloted_to_broker=new int[b_count][c_count];
                    
                    for (int y = 0; y <(c_count+b_count); ++y){
                        
                        int no_request=0;   

                        // saving broker request in each iteration
                        for (int br = 0; br < b_count; ++br){
                            for (int c = 0; c < c_count; ++c){
                                request_list.add(new ArrayList<Integer>());
                            }                           
                            if(broker_demand[br]>0){
                                no_request++;
                                // System.out.print("\nbroker :"+(br+1) +" demand :"+broker_demand[br]);
                                int cloud_id=broker_preference[br][br_current_pref[br]];
                                
                                System.out.print("cloudid: "+cloud_id+1+"\n");
                                br_current_pref[br]++;
                                if(br_current_pref[br]>c_count-1)
                                br_current_pref[br]=c_count-1;
                                // request_list.get(0).add(0,12);
                                if(cloud_id<c_count) //47
                                request_list.get(cloud_id).add(br);
                            }
                        }
                        if(no_request==0){
                                System.out.print("\n\n******ALL BROKER DEMAND MET WITH STABLE ALLOTMENT********\n\nfinal allotment\n");
                
                                show_vm_allotments(vm_alloted_to_broker,c_count,b_count,broker_demand);
                                break;
                        }
                
                        System.out.print("\n------------------------------------------------\niteration:"+(y+1)+"\n");
                
                        //sort the request list
                        for (int ii = 0; ii < c_count; ++ii){ 
                                for (int j = 0; j < request_list.get(ii).size() ; ++j){
                                    int max=request_list.get(ii).get(j); //broker id
                                    // System.out.print+"\nmax:"+max;
                                    //int max=0;
                                    for (int k = j+1; k <request_list.get(ii).size();  ++k){
                                        if(price_matrix[max][ii]<price_matrix[request_list.get(ii).get(k)][ii]){                          
                                                //swap
                                                request_list.get(ii).set(j,request_list.get(ii).get(k));
                                                request_list.get(ii).set(k,max);
                                                max=request_list.get(ii).get(j);

                                        }
                                    }
                                }   
                        }
                
                        System.out.print("\n\nRequest list sorted as per cloud preference:\n ");           
                        for (int x = 0; x < b_count; ++x){  
                            System.out.print("\ncloud "+(x+1)+": "); 
                                for (int j = 0; j < request_list.get(x).size(); ++j){
                                    System.out.print("B"+(request_list.get(x).get(j)+1)+" ");
                                }
                                
                            }

                        for (int i = 0; i < c_count; ++i){
            
                            residual_vms_cloud[i]=cloud_supply[i];
                            //System.out.print("\nsupply:\n"+cloud_supply[i]);

                                //(taking back previous allotment and reassigning again)
                                for (int m = 0; m < request_list.get(i).size(); ++m){ //47
                                    broker_demand[request_list.get(i).get(m)]+=vm_alloted_to_broker[request_list.get(i).get(m)][i];
                                    vm_alloted_to_broker[request_list.get(i).get(m)][i]=0;
                                }
                                
                               // System.out.print("\ncloud "+(i+1)+"  allotment\n");
                                for (int j = 0; j < request_list.get(i).size(); ++j)
                                {
                                    if(residual_vms_cloud[i]>0 ){

                                        int demand=broker_demand[request_list.get(i).get(j)];
                                        //  System.out.print("\nB:"+(request_list.get(i).get(j)+1)+" demand:"+demand+"\n");
                                        if(demand<residual_vms_cloud[i]){
        
                                            residual_vms_cloud[i]-=demand;
                                            //System.out.print+"\nresidual_vms_cloud[i]"+residual_vms_cloud[i];
                                            broker_demand[request_list.get(i).get(j)]=0;
                                            vm_alloted_to_broker[request_list.get(i).get(j)][i]=vm_alloted_to_broker[request_list.get(i).get(j)][i]+demand;
                                        }
                                        else{   
                                                broker_demand[request_list.get(i).get(j)]=demand+vm_alloted_to_broker[request_list.get(i).get(j)][i]-residual_vms_cloud[i];
                                                vm_alloted_to_broker[request_list.get(i).get(j)][i]=residual_vms_cloud[i];
                                                
                                                // System.out.print+"\nsatish"+demand-residual_vms_cloud[i];
                                                broker_demand[request_list.get(i).get(j)]=demand-residual_vms_cloud[i];
                                                // System.out.print("\n remianing request for broker "+request_list.get(i).get(j)+1+":"+broker_demand[request_list.get(i).get(j)]+"\n");
                                                residual_vms_cloud[i]=0;
                                        }

                                    }
                                    else{
                                            request_list.get(i).size();
                                            request_list.get(i).remove(j); 
                                            break;
                                    }
                                }
                        
                        }

                        System.out.print("\n vms alloted in "+(y+1)+" :iteration\n\n");
                        show_vm_allotments(vm_alloted_to_broker,c_count,b_count,broker_demand); //47
                        System.out.print("Broker demand after "+y+" allotment:\n");

                        for (int x = 0; x < b_count; ++x){
                                System.out.print("  B"+(x+1)+":"+broker_demand[x]);
                        }

                    }                    
                }
                //if cloud making request
                else{
                    
                    int cloud_current_pref[] = new int[c_count]; 
                    int [] pending_demand_br=new int[b_count];
                    for(int x=0;x<broker_demand.length;x++)
                    pending_demand_br[x]=broker_demand[x];

                    ArrayList<ArrayList<Integer> > request_list = new ArrayList<ArrayList<Integer> >(b_count);    

                    for (int y = 0; y <(c_count+b_count); ++y){
                                
                                int all_Demand=0;
                                int no_request=0;
                            for (int x = 0; x < b_count; ++x){
                                all_Demand+=pending_demand_br[x];
                            }    
                            // saving broker request in each iteration
                            for (int br = 0; br < b_count; ++br){
                                request_list.add(new ArrayList<Integer>());
                            }
                            for (int cl = 0; cl < c_count; ++cl){
                            
                                System.out.print("\nbroker :"+(cl+1) +" demand :"+pending_demand_br[cl]);

                                if(cloud_supply[cl]>0)
                                {
                                    no_request++;
                                    int broker_id=cloud_preference[cl][cloud_current_pref[cl]];
                                 //   System.out.print("cloudid:"+(broker_id+1)+"\n ");
                                    cloud_current_pref[cl]++;
                                    if(cloud_current_pref[cl]>b_count-1)
                                    cloud_current_pref[cl]=b_count-1;
                                    // request_list.get(0).add(0,12);
                                
                                request_list.get(broker_id).add(cl);
                                }

                            }
                            System.out.print("all demands :"+all_Demand+" \n"); 
                            if(all_Demand==0 || no_request==0 ){
                                    System.out.print("\n\n******ALL BROKER DEMAND MET WITH STABLE ALLOTMENT********\n\nfinal allotment\n");
                    
                                    show_vm_allotments(vm_alloted_to_broker,c_count,b_count,broker_demand);
                                    break;
                            }
                    
                            System.out.print("\n------------------------------------------------\niteration:"+(y+1)+"\n");
                            //sort the request list
                            for (int ii = 0; ii < c_count; ++ii){      
                                    for (int j = 0; j < request_list.get(ii).size() ; ++j){
                                        int min=request_list.get(ii).get(j); //broker id
                                        for (int k = j+1; k <request_list.get(ii).size();  ++k){
                                            if(transpose_price_matrix[min][ii]>transpose_price_matrix[request_list.get(ii).get(k)][ii]){
                                                    
                                                    request_list.get(ii).set(j,request_list.get(ii).get(k));
                                                    request_list.get(ii).set(k,min);
                                                    min=request_list.get(ii).get(j);

                                                }
                                        }
                                    }   
                            }
                    
                            System.out.print("\n\nRequest list sorted as per borker preference:\n ");
                    
                            for (int x = 0; x < b_count; ++x){  
                                System.out.print("\nBroker "+(x+1)+": ");
                                    for (int j = 0; j < request_list.get(x).size(); ++j){
                                        System.out.print("C"+(request_list.get(x).get(j)+1)+" ");
                                    }
                                    
                                }

                            for (int i = 0; i < b_count; ++i){
         
                                pending_demand_br[i]=broker_demand[i];
                            
                                //(taking back previous allotment and reassigning again)
                                for (int m = 0; m < request_list.get(i).size(); ++m){
                                    cloud_supply[request_list.get(i).get(m)]+=vm_alloted_to_broker[i][request_list.get(i).get(m)];
                                    vm_alloted_to_broker[i][request_list.get(i).get(m)]=0;
                                }
                                    

                            //System.out.print("\nbroker  "+(i+1)+"  allotment\n");
                            for (int j = 0; j < request_list.get(i).size(); ++j)
                            {
                            //     System.out.print("\nsupply:\n"+pending_demand_br[i]);
                                if(pending_demand_br[i]>0 ){
                            
                                    int supply=cloud_supply[request_list.get(i).get(j)];
                                    //  System.out.print("\nB:"+(request_list.get(i).get(j)+1)+" supply:"+supply+"\n");
                                    if(supply<pending_demand_br[i]){
                                        pending_demand_br[i]-=supply;
                                       cloud_supply[request_list.get(i).get(j)]=0;

                                        // vm_alloted_to_broker[request_list.get(i).get(i)][j]=vm_alloted_to_broker[request_list.get(i).get(i)][j]+supply;
                                        vm_alloted_to_broker[i][request_list.get(i).get(j)]=supply;
                                    }

                                    else{  
                                          vm_alloted_to_broker[i][request_list.get(i).get(j)]=pending_demand_br[i];  
                                            // System.out.print+"\nsatish"+supply-pending_demand_br[i];
                                            cloud_supply[request_list.get(i).get(j)]=supply-pending_demand_br[i];
                                            pending_demand_br[i]=0;
                                    }

                                }
                                else{
                                        request_list.get(i).size();
                                        request_list.get(i).remove(j); //if error comment this @47
                                        break;
                                }
                            }
                            
                        }

                            System.out.print("\n vms alloted in "+(y+1)+" :iteration\n\n");
                    show_vm_allotments(vm_alloted_to_broker,c_count,b_count,broker_demand); //47
                        

                        System.out.print("Broker demand after "+y+" allotment:\n");

                            for (int x = 0; x < c_count; ++x){
                                    System.out.print("  C"+(x+1)+":"+cloud_supply[x]);
                            }
                    }    
                }    
                    //utility calcution for broker
                    if(choice2==1)
                    System.out.print("\n  Utility when BROKER made request\n"); 
                   else
                   System.out.print("\n  Utility when CLOUD made request\n"); 
                //using broker prederence and vm allotment
                int [] broker_utility=new int[b_count];
                int index=0;
                System.out.print("\n");
                for (int i = 0; i < b_count; ++i)
                {
                    // System.out.print((i+1)+"broker\n");
                    for (int j = 0; j < c_count; ++j){
                    
                        for (int k = 0; k <c_count; k++)
                        if (broker_preference[i][k] == j)
                        { index=k;break;}      
                       // System.out.print("  util:"+(c_count-index)*vm_alloted_to_broker[i][j]);
                        broker_utility[i]+=(c_count-index)*vm_alloted_to_broker[i][j];
            
                    }
                }
            
                int [] cloud_utility=new int[c_count];
            
                System.out.print("\n");
                for (int i = 0; i < c_count; ++i){
                  //  System.out.print("cloud\n");
                    for (int j = 0; j < b_count; ++j)
                    {
                        for (int k = 0; k <b_count; k++)
                        if (cloud_preference[i][k] == j)
                        { index=k;break;}
                        
                       // System.out.print(" uti:"+(b_count-index+2)*vm_alloted_to_broker[j][i]);
                        cloud_utility[i]+=(b_count-index)*vm_alloted_to_broker[j][i];
            
                    }
                }
            
                System.out.print("broker utility\n");
                for (int i = 0; i < b_count; ++i)
                {
                    System.out.print("B"+(i+1)+":"+broker_utility[i]+"\n");
                }
                System.out.print("cloud utility\n") ;     
                for (int i = 0; i < c_count; ++i) 
                {
                    System.out.print("C"+(i+1)+":"+cloud_utility[i]+"\n");
                }
            
                //utility calcution for broker    
            }                         
        }  