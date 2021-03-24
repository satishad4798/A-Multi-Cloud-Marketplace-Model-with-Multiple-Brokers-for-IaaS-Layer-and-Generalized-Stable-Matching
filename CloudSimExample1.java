package org.cloudbus.cloudsim.examples;
import java.util.*;

/*
 * Title:        CloudSim Toolkit
 * Description:  CloudSim (Cloud Simulation) Toolkit for Modeling and Simulation
 *               of Clouds
 * Licence:      GPL - http://www.gnu.org/copyleft/gpl.html
 *
 * Copyright (c) 2009, The University of Melbourne, Australia
 */

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.CloudletSchedulerTimeShared;
import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.DatacenterCharacteristics;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.UtilizationModel;
import org.cloudbus.cloudsim.UtilizationModelFull;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.VmAllocationPolicySimple;
import org.cloudbus.cloudsim.VmSchedulerTimeShared;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;

/**
 * A simple example showing how to create a datacenter with one host and run one
 * cloudlet on it.
 */
public class CloudSimExample1 {

	/** The cloudlet list. */
	private static List<Cloudlet> cloudletList;

	/** The vmlist. */
	private static List<Vm> vmlist;

	/**
	 * Creates main() to run this example.
	 *
	 * @param args the args
	 */
	@SuppressWarnings("unused")
	
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
	    	
	
	public static void main(String[] args) {

		Log.printLine("Starting CloudSimExample1...");
		
//		satish




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

          
            
		//satish
		
		

		try {
			// First step: Initialize the CloudSim package. It should be called
			// before creating any entities.
			int num_user = 1; // number of cloud users
			Calendar calendar = Calendar.getInstance();
			boolean trace_flag = false; // mean trace events

			// Initialize the CloudSim library
			CloudSim.init(num_user, calendar, trace_flag);

			// Second step: Create Datacenters
			// Datacenters are the resource providers in CloudSim. We need at
			// list one of them to run a CloudSim simulation
			Datacenter datacenter0 = createDatacenter("Datacenter_0");

			// Third step: Create Broker
			DatacenterBroker broker = createBroker();
			int brokerId = broker.getId();

			// Fourth step: Create one virtual machine
			vmlist = new ArrayList<Vm>();

			// VM description
			int vmid = 0;
			int mips = 1000;
			long size = 10000; // image size (MB)
			int ram = 512; // vm memory (MB)
			long bw = 1000;
			int pesNumber = 1; // number of cpus
			String vmm = "Xen"; // VMM name

			// create VM
			Vm vm = new Vm(vmid, brokerId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());

			// add the VM to the vmList
			vmlist.add(vm);

			// submit vm list to the broker
			broker.submitVmList(vmlist);

			// Fifth step: Create one Cloudlet
			cloudletList = new ArrayList<Cloudlet>();

			// Cloudlet properties
			int id = 0;
			long length = 400000;
			long fileSize = 300;
			long outputSize = 300;
			UtilizationModel utilizationModel = new UtilizationModelFull();

			Cloudlet cloudlet = new Cloudlet(id, length, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
			cloudlet.setUserId(brokerId);
			cloudlet.setVmId(vmid);

			// add the cloudlet to the list
			cloudletList.add(cloudlet);

			// submit cloudlet list to the broker
			broker.submitCloudletList(cloudletList);

			// Sixth step: Starts the simulation
			CloudSim.startSimulation();

			CloudSim.stopSimulation();

			//Final step: Print results when simulation is over
			List<Cloudlet> newList = broker.getCloudletReceivedList();
			printCloudletList(newList);

			Log.printLine("CloudSimExample1 finished!");
		} catch (Exception e) {
			e.printStackTrace();
			Log.printLine("Unwanted errors happen");
		}
	}

	/**
	 * Creates the datacenter.
	 *
	 * @param name the name
	 *
	 * @return the datacenter
	 */
	private static Datacenter createDatacenter(String name) {

		// Here are the steps needed to create a PowerDatacenter:
		// 1. We need to create a list to store
		// our machine
		List<Host> hostList = new ArrayList<Host>();

		// 2. A Machine contains one or more PEs or CPUs/Cores.
		// In this example, it will have only one core.
		List<Pe> peList = new ArrayList<Pe>();

		int mips = 1000;

		// 3. Create PEs and add these into a list.
		peList.add(new Pe(0, new PeProvisionerSimple(mips))); // need to store Pe id and MIPS Rating

		// 4. Create Host with its id and list of PEs and add them to the list
		// of machines
		int hostId = 0;
		int ram = 2048; // host memory (MB)
		long storage = 1000000; // host storage
		int bw = 10000;

		hostList.add(
			new Host(
				hostId,
				new RamProvisionerSimple(ram),
				new BwProvisionerSimple(bw),
				storage,
				peList,
				new VmSchedulerTimeShared(peList)
			)
		); // This is our machine

		// 5. Create a DatacenterCharacteristics object that stores the
		// properties of a data center: architecture, OS, list of
		// Machines, allocation policy: time- or space-shared, time zone
		// and its price (G$/Pe time unit).
		String arch = "x86"; // system architecture
		String os = "Linux"; // operating system
		String vmm = "Xen";
		double time_zone = 10.0; // time zone this resource located
		double cost = 3.0; // the cost of using processing in this resource
		double costPerMem = 0.05; // the cost of using memory in this resource
		double costPerStorage = 0.001; // the cost of using storage in this
										// resource
		double costPerBw = 0.0; // the cost of using bw in this resource
		LinkedList<Storage> storageList = new LinkedList<Storage>(); // we are not adding SAN
													// devices by now

		DatacenterCharacteristics characteristics = new DatacenterCharacteristics(
				arch, os, vmm, hostList, time_zone, cost, costPerMem,
				costPerStorage, costPerBw);

		// 6. Finally, we need to create a PowerDatacenter object.
		Datacenter datacenter = null;
		try {
			datacenter = new Datacenter(name, characteristics, new VmAllocationPolicySimple(hostList), storageList, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return datacenter;
	}

	// We strongly encourage users to develop their own broker policies, to
	// submit vms and cloudlets according
	// to the specific rules of the simulated scenario
	/**
	 * Creates the broker.
	 *
	 * @return the datacenter broker
	 */
	private static DatacenterBroker createBroker() {
		DatacenterBroker broker = null;
		try {
			broker = new DatacenterBroker("Broker");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return broker;
	}

	/**
	 * Prints the Cloudlet objects.
	 *
	 * @param list list of Cloudlets
	 */
	private static void printCloudletList(List<Cloudlet> list) {
		int size = list.size();
		Cloudlet cloudlet;

		String indent = "    ";
		Log.printLine();
		Log.printLine("========== OUTPUT ==========");
		Log.printLine("Cloudlet ID" + indent + "STATUS" + indent
				+ "Data center ID" + indent + "VM ID" + indent + "Time" + indent
				+ "Start Time" + indent + "Finish Time");

		DecimalFormat dft = new DecimalFormat("###.##");
		for (int i = 0; i < size; i++) {
			cloudlet = list.get(i);
			Log.print(indent + cloudlet.getCloudletId() + indent + indent);

			if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS) {
				Log.print("SUCCESS");

				Log.printLine(indent + indent + cloudlet.getResourceId()
						+ indent + indent + indent + cloudlet.getVmId()
						+ indent + indent
						+ dft.format(cloudlet.getActualCPUTime()) + indent
						+ indent + dft.format(cloudlet.getExecStartTime())
						+ indent + indent
						+ dft.format(cloudlet.getFinishTime()));
			}
		}
	}

}