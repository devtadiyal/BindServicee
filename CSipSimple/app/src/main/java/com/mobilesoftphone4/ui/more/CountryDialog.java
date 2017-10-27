package com.mobilesoftphone4.ui.more;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;

import com.mobilesoftphone4.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressLint("NewApi")
public class CountryDialog extends Activity implements SearchView.OnQueryTextListener
{
	  
	ListView listview;
	SearchView searchView;
	List<Map<String, Object>> list;
	 
	public static String countryCode;
	public static String countryName;
	public static int countryFlag;
	public static String countryId;
	public static ImageView flag;
	
	 Object f,c,n;
	
	public static final String myData = "mySharedPreferences";
	int mode=Context.MODE_PRIVATE;
	
	public static String savedCountryCode="Country";
	public static String savedCountryName="Seleziona il paese";
	public static int savedCountryFlag=R.drawable.unitedstates;
	public static String savedCountryId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.country_dialog);
		
		searchView=(SearchView)findViewById(R.id.search_view);
		listview=(ListView)findViewById(R.id.countryList);
		
		final List<Map<String,Object>> listData=getListData();
		 
		SimpleAdapter adapter =new SimpleAdapter(this, (List<? extends Map<String, ?>>) listData, R.layout.country_list_row, new String [] {"countryFlag","countryName","countryCode","countryId"}, new int[]{R.id.COUNTRY_FLAG,R.id.COUNTRY_NAME,R.id.COUNTRY_CODE});
		 
		listview.setAdapter(adapter);
		
         listview.setTextFilterEnabled(true);
		 
		 setupSearchView();
	
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id)
			{
				 //Toast.makeText(CountryDialog.this, "You selected : " +list.get(position), Toast.LENGTH_SHORT).show();
				
				  Map<String, Object> map=list.get(position);
					
					for(Map.Entry<String, Object> entry :map.entrySet())
					{
						System.out.println("value of :"+position);
						//System.out.println("key : "+entry.getKey()+" and value :"+entry.getValue());
						String key=entry.getKey();
						
						System.out.println("value of key : "+key);
						
					if(key.equals("countryCode"))
						{
							f=entry.getValue();
							countryCode=String.valueOf(f);
							System.out.println("inside countryCode : "+countryCode);
						}
					if(key.equals("countryFlag"))	
						{
							c=entry.getValue();
							countryFlag=(Integer)c;
						    System.out.println("inside countryFlag : "+countryFlag);
						}
					if(key.equals("countryName"))
						{
							n=entry.getValue();
							countryName=String.valueOf(n);
							System.out.println("inside countryName : "+countryName+"\n");
						}
					if(key.equals("countryId"))
					{
						n=entry.getValue();
						countryId=String.valueOf(n);
						System.out.println("inside countryName : "+countryId+"\n");
					}
					}		
					setSavedContryValues(countryName, countryCode,countryFlag,countryId);
					finish();
				}
			});	
		getSavedCountryValues();
		}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getSavedCountryValues();
	}
	
	public void setSavedContryValues(String name, String code,int flag,String Id)
	{
		
		SharedPreferences sharedPreference=this.getSharedPreferences(myData, mode);
		
		SharedPreferences.Editor editer= sharedPreference.edit();
		
		editer.putString("CNAME", name);
		editer.putString("CCODE", code);
		editer.putInt("CFLAG", flag);
		editer.putString("CId",Id);
		
		editer.commit();
		
		getSavedCountryValues();
		
	}
	
	public void getSavedCountryValues()
	{
		SharedPreferences sharedPreference=this.getSharedPreferences(myData, mode);
		
		savedCountryName=sharedPreference.getString("CNAME", "");
		savedCountryCode=sharedPreference.getString("CCODE", "");
		savedCountryFlag=sharedPreference.getInt("CFLAG", 0);
		savedCountryId=sharedPreference.getString("CId", "");
		
		System.out.println(" saveed value in load method in CountryDialog  countryCode : "+countryCode);
		System.out.println(" saved value in load method  in CountryDialog   countryName : "+countryName);
		System.out.println(" saved value in load method  in CountryDialog    countryFlag : "+countryFlag);
		System.out.println(" saved value in load method  in CountryDialog    countryId : "+countryId);
	}
		
	public List<Map<String, Object>> getListData() 
	{
		list=new ArrayList<Map<String, Object>>();
		
		Map<String, Object> map1=new HashMap<String, Object>();
		map1.put("countryFlag", R.drawable.unitedstates);
		map1.put("countryName", "USA");
		map1.put("countryCode", "+1");
		map1.put("countryId", "1");
		
		list.add(map1);
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("countryFlag", R.drawable.unitedkingdom);
		map.put("countryName", "United Kingdom");
		map.put("countryCode", "+44");
		map.put("countryId", "224");
		
		list.add(map);
		
		Map<String, Object> map2=new HashMap<String, Object>();
		map2.put("countryFlag", R.drawable.albania);
		map2.put("countryName", "Albania");
		map2.put("countryCode", "+355");
		map2.put("countryId", "2");
		
		list.add(map2);
		
		
		Map<String, Object> map3=new HashMap<String, Object>();
		map3.put("countryFlag", R.drawable.algeria);
		map3.put("countryName", "Algeria");
		map3.put("countryCode", "+213");
		map3.put("countryId", "3");
		
		list.add(map3);
		
		Map<String, Object> map4=new HashMap<String, Object>();
		map4.put("countryFlag", R.drawable.americansamoa);
		map4.put("countryName", "American Samoa");
		map4.put("countryCode", "+684");
		map4.put("countryId", "4");
		
		list.add(map4);
		
		Map<String, Object> map5=new HashMap<String, Object>();
		map5.put("countryFlag", R.drawable.andorra);
		map5.put("countryName", "Andorra");
		map5.put("countryCode", "5");
		map5.put("countryId", "5");
		
		list.add(map5);
		
		Map<String, Object> map6=new HashMap<String, Object>();
		map6.put("countryFlag", R.drawable.angola);
		map6.put("countryName", "Angola");
		map6.put("countryCode", "6");
		map6.put("countryId", "6");
		
		list.add(map6);
		
		Map<String, Object> map7=new HashMap<String, Object>();
		map7.put("countryFlag", R.drawable.argentina);
		map7.put("countryName", "Argentina");
		map7.put("countryCode", "10");
		map7.put("countryId", "10");
		
		list.add(map7);
		
		Map<String, Object> map8=new HashMap<String, Object>();
		map8.put("countryFlag", R.drawable.anguilla);
		map8.put("countryName", "Anguilla");
		map8.put("countryCode", "+1264");
		map8.put("countryId", "7");
		
		list.add(map8);
		
		Map<String, Object> map9=new HashMap<String, Object>();
		map9.put("countryFlag", R.drawable.australia);
		map9.put("countryName", "Australia");
		map9.put("countryCode", "13");
		
		list.add(map9);
		
		Map<String, Object> map10=new HashMap<String, Object>();
		map10.put("countryFlag", R.drawable.afganistan);
		map10.put("countryName", "Afganistan");
		map10.put("countryCode", "+93");
		map10.put("countryId", "1");
			
		list.add(map10);
		
		Map<String, Object> map11=new HashMap<String, Object>();
		map11.put("countryFlag", R.drawable.bahamas);
		map11.put("countryName", "Bahamas");
		map11.put("countryCode", "16");
		
		list.add(map11);
		
		Map<String, Object> map12=new HashMap<String, Object>();
		map12.put("countryFlag", R.drawable.bangladesh);
		map12.put("countryName", "Bangladesh");
		map12.put("countryCode", "+880");
		map12.put("countryId", "18");
		
		list.add(map12);
		
		Map<String, Object> map13=new HashMap<String, Object>();
		map13.put("countryFlag", R.drawable.barbados);
		map13.put("countryName", "Barbados");
		map13.put("countryCode", "+1246");
		map13.put("countryId", "19");
		
		list.add(map13);
		
		Map<String, Object> map14=new HashMap<String, Object>();
		map14.put("countryFlag", R.drawable.bahrain);
		map14.put("countryName", "Bahrain");
		map14.put("countryCode", "+973");
		map14.put("countryId", "17");
			
		list.add(map14);
		
		Map<String, Object> map15=new HashMap<String, Object>();
		map15.put("countryFlag", R.drawable.belgiumcivil);
		map15.put("countryName", "Belgium Civil");
		map15.put("countryCode", "21");
		
		list.add(map15);
		
		Map<String, Object> map16=new HashMap<String, Object>();
		map16.put("countryFlag", R.drawable.bermuda);
		map16.put("countryName", "Bermuda");
		map16.put("countryCode", "24");
		
		list.add(map16);
		
		
		Map<String, Object> map17=new HashMap<String, Object>();
		map17.put("countryFlag", R.drawable.benin);
		map17.put("countryName", "Benin");
		map17.put("countryCode", "+229");
		map17.put("countryId", "23");
		
		list.add(map17);
		
		Map<String, Object> map18=new HashMap<String, Object>();
		map18.put("countryFlag", R.drawable.bhutan);
		map18.put("countryName", "Bhutan");
		map18.put("countryCode", "25");
		
		list.add(map18);
		

		Map<String, Object> map19=new HashMap<String, Object>();
		map19.put("countryFlag", R.drawable.bolivia);
		map19.put("countryName", "Bolivia");
		map19.put("countryCode", "+591");
		map19.put("countryId", "26");
			
		list.add(map19);
		
		Map<String, Object> map20=new HashMap<String, Object>();
		map20.put("countryFlag", R.drawable.brazil);
		map20.put("countryName", "Brazil");
		map20.put("countryCode", "+55");
		map20.put("countryId", "30");
		
		list.add(map20);
		
		Map<String, Object> map21=new HashMap<String, Object>();
		map21.put("countryFlag", R.drawable.brunei);
		map21.put("countryName", "Brunei");
		map21.put("countryCode", "+673");
		map21.put("countryId", "6252");
			
		list.add(map21);
		
		Map<String, Object> map22=new HashMap<String, Object>();
		map22.put("countryFlag", R.drawable.burundi);
		map22.put("countryName", "Burundi");
		map22.put("countryCode", "+257");
		map22.put("countryId", "35");
			
		list.add(map22);
		
		Map<String, Object> map23=new HashMap<String, Object>();
		map23.put("countryFlag", R.drawable.cambodia);
		map23.put("countryName", "Cambodia");
		map23.put("countryCode", "+855");
		map23.put("countryId", "36");
			
		list.add(map23);
		
		Map<String, Object> map24=new HashMap<String, Object>();
		map24.put("countryFlag", R.drawable.cameroon);
		map24.put("countryName", "Cameroon");
		map24.put("countryCode", "+237");
		map24.put("countryId", "37");
			
		list.add(map24);
		
		Map<String, Object> map25=new HashMap<String, Object>();
		map25.put("countryFlag", R.drawable.centralafricanrepublic);
		map25.put("countryName", "Central African Republic");
		map25.put("countryCode", "+236");
		map25.put("countryId", "41");
			
		list.add(map25);
		
		Map<String, Object> map26=new HashMap<String, Object>();
		map26.put("countryFlag", R.drawable.canada);
		map26.put("countryName", "Canada");
		map26.put("countryCode", "38");
		map26.put("countryId", "38");
		
		list.add(map26);
		
		Map<String, Object> map27=new HashMap<String, Object>();
		map27.put("countryFlag", R.drawable.china);
		map27.put("countryName", "China");
		map27.put("countryCode", "+86");
		map27.put("countryId", "44");
		
		list.add(map27);
		
		Map<String, Object> map28=new HashMap<String, Object>();
		map28.put("countryFlag", R.drawable.chile);
		map28.put("countryName", "Chile");
		map28.put("countryCode", "+56");
		map28.put("countryId", "43");
		
		list.add(map28);
		
		Map<String, Object> map29=new HashMap<String, Object>();
		map29.put("countryFlag", R.drawable.colombia);
		map29.put("countryName", "Colombia");
		map29.put("countryCode", "+57");
		map29.put("countryId", "47");
		
		list.add(map29);
		
		Map<String, Object> map30=new HashMap<String, Object>();
		map30.put("countryFlag", R.drawable.comoros);
		map30.put("countryName", "Comoros");
		map30.put("countryCode", "48");
		
		list.add(map30);
		
		Map<String, Object> map31=new HashMap<String, Object>();
		map31.put("countryFlag", R.drawable.cuba);
		map31.put("countryName", "Cuba");
		map31.put("countryCode", "+53");
		map31.put("countryId", "55");
		
		list.add(map31);
		
		Map<String, Object> map32=new HashMap<String, Object>();
		map32.put("countryFlag", R.drawable.denmark);
		map32.put("countryName", "denmark");
		map32.put("countryCode", "58");
		map32.put("countryId", "58");
		
		list.add(map32);
		
		Map<String, Object> map33=new HashMap<String, Object>();
		map33.put("countryFlag", R.drawable.dominica);
		map33.put("countryName", "Dominica");
		map33.put("countryCode", "+1767");
		map33.put("countryId", "60");
			
		list.add(map33);
		
		
		Map<String, Object> map34=new HashMap<String, Object>();
		map34.put("countryFlag", R.drawable.ecuador);
		map34.put("countryName", "Ecuador");
		map34.put("countryCode", "+593");
		map34.put("countryId", "62");
		
		list.add(map34);
		
		Map<String, Object> map35=new HashMap<String, Object>();
		map35.put("countryFlag", R.drawable.elsalvador);
		map35.put("countryName", "El Salvador");
		map35.put("countryCode", "+503");
		map35.put("countryId", "64");
			
		list.add(map35);
		
		Map<String, Object> map351=new HashMap<String, Object>();
		map351.put("countryFlag", R.drawable.albania);
		map351.put("countryName", "Albania");
		map351.put("countryCode", "+355");
		map351.put("countryId", "2");
		list.add(map22);
		
		
		Map<String, Object> map36=new HashMap<String, Object>();
		map36.put("countryFlag", R.drawable.egypt);
		map36.put("countryName", "Egypt");
		map36.put("countryCode", "+20");
		map36.put("countryId", "63");
		
		list.add(map36);
		
		Map<String, Object> map37=new HashMap<String, Object>();
		map37.put("countryFlag", R.drawable.ethiopia);
		map37.put("countryName", "Ethiopia");
		map37.put("countryCode", "68");
		map37.put("countryId", "68");
		list.add(map37);
		
		Map<String, Object> map38=new HashMap<String, Object>();
		map38.put("countryFlag", R.drawable.finland);
		map38.put("countryName", "finland");
		map38.put("countryCode", "+358");
		map38.put("countryId", "72");
		
		list.add(map38);
		
		Map<String, Object> map39=new HashMap<String, Object>();
		map39.put("countryFlag", R.drawable.fiji);
		map39.put("countryName", "Fiji");
		map39.put("countryCode", "+679");
		map39.put("countryId", "71");
		
		list.add(map39);
		
		Map<String, Object> map40=new HashMap<String, Object>();
		map40.put("countryFlag", R.drawable.france);
		map40.put("countryName", "France");
		map40.put("countryCode", "+33");
		map40.put("countryId", "73");
		
		list.add(map40);
		
		Map<String, Object> map41=new HashMap<String, Object>();
		map41.put("countryFlag", R.drawable.guinea);
		map41.put("countryName", "Guinea Bissau");
		map41.put("countryCode", "+240");
		map41.put("countryId", "90");
			
		list.add(map41);
		
		Map<String, Object> map42=new HashMap<String, Object>();
		map42.put("countryFlag", R.drawable.germany);
		map42.put("countryName", "Germany");
		map42.put("countryCode", "+49");
		map42.put("countryId", "80");
		list.add(map42);
		
		Map<String, Object> map43=new HashMap<String, Object>();
		map43.put("countryFlag", R.drawable.ghana);
		map43.put("countryName", "Ghana");
		map43.put("countryCode", "+233");
		map43.put("countryId", "81");
		
		list.add(map43);
		
		Map<String, Object> map44=new HashMap<String, Object>();
		map44.put("countryFlag", R.drawable.greece);
		map44.put("countryName", "Greece");
		map44.put("countryCode", "+30");
		map44.put("countryId", "83");
		
		list.add(map44);
		
		Map<String, Object> map45=new HashMap<String, Object>();
		map45.put("countryFlag", R.drawable.grenada);
		map45.put("countryName", "Grenada");
		map45.put("countryCode", "+1273");
		map45.put("countryId", "85");
		
		list.add(map45);
		
		Map<String, Object> map46=new HashMap<String, Object>();
		map46.put("countryFlag", R.drawable.haiti);
		map46.put("countryName", "Haiti");
		map46.put("countryCode", "+509");
		map46.put("countryId", "92");
			
		list.add(map46);
		
		Map<String, Object> map47=new HashMap<String, Object>();
		map47.put("countryFlag", R.drawable.hongkong);
		map47.put("countryName", "Hongkong");
		map47.put("countryCode", "96");
		map47.put("countryId", "96");
	
		list.add(map47);
		
		
		Map<String, Object> map48=new HashMap<String, Object>();
		map48.put("countryFlag", R.drawable.hungary);
		map48.put("countryName", "Hungary");
		map48.put("countryCode", "97");
		map48.put("countryId", "97");
		
		list.add(map48);
		
		Map<String, Object> map49=new HashMap<String, Object>();
		map49.put("countryFlag", R.drawable.india);
		map49.put("countryName", "India");
		map49.put("countryCode", "+91");
		map49.put("countryId", "99");
		
		list.add(map49);
		
		
		Map<String, Object> map50=new HashMap<String, Object>();
		map50.put("countryFlag", R.drawable.indonesia);
		map50.put("countryName", "Indonesia");
		map50.put("countryCode", "+62");
		map50.put("countryId", "100");
		
		list.add(map50);
		
		Map<String, Object> map51=new HashMap<String, Object>();
		map51.put("countryFlag", R.drawable.iran);
		map51.put("countryName", "Iran");
		map51.put("countryCode", "101");
		map51.put("countryId", "101");
		
		list.add(map51);
		
		Map<String, Object> map52=new HashMap<String, Object>();
		map52.put("countryFlag", R.drawable.iraq);
		map52.put("countryName", "Iraq");
		map52.put("countryCode", "+964");
		map52.put("countryId", "102");
		
		list.add(map52); 
		
		Map<String, Object> map53=new HashMap<String, Object>();
		map53.put("countryFlag", R.drawable.ireland);
		map53.put("countryName", "Ireland");
		map53.put("countryCode", "+353");
		map53.put("countryId", "103");
		
		list.add(map53);
		
		Map<String, Object> map54=new HashMap<String, Object>();
		map54.put("countryFlag", R.drawable.israel);
		map54.put("countryName", "Israel");
		map54.put("countryCode", "+972");
		map54.put("countryId", "104");
		
		list.add(map54);
		
		Map<String, Object> map55=new HashMap<String, Object>();
		map55.put("countryFlag", R.drawable.italy);
		map55.put("countryName", "Italy");
		map55.put("countryCode", "+39");
		map55.put("countryId", "105");
		
		list.add(map55);
		
		Map<String, Object> map56=new HashMap<String, Object>();
		map56.put("countryFlag", R.drawable.jamaica);
		map56.put("countryName", "Jamaica");
		map56.put("countryCode", "+1876");
		map56.put("countryId", "106");
			
		list.add(map56);
		
		Map<String, Object> map57=new HashMap<String, Object>();
		map57.put("countryFlag", R.drawable.jordan);
		map57.put("countryName", "Jordan");
		map57.put("countryCode", "+962");
		map57.put("countryId", "108");
			
		list.add(map57);
		
		Map<String, Object> map58=new HashMap<String, Object>();
		map58.put("countryFlag", R.drawable.kazakhstan);
		map58.put("countryName", "Kazakhstan");
		map58.put("countryCode", "+7");
		map58.put("countryId", "109");
			
		list.add(map58);
		
		Map<String, Object> map59=new HashMap<String, Object>();
		map59.put("countryFlag", R.drawable.kenya);
		map59.put("countryName", "Kenya");
		map59.put("countryCode", "+254");
		map59.put("countryId", "110");
		
		list.add(map59);
		
		Map<String, Object> map60=new HashMap<String, Object>();
		map60.put("countryFlag", R.drawable.kuwait);
		map60.put("countryName", "Kuwait");
		map60.put("countryCode", "+965");
		map60.put("countryId", "114");
		
		list.add(map60);
		
		Map<String, Object> map61=new HashMap<String, Object>();
		map61.put("countryFlag", R.drawable.laos);
		map61.put("countryName", "Laos");
		map61.put("countryCode", "+856");
		map61.put("countryId", "116");
			
		list.add(map61);
		
		Map<String, Object> map62=new HashMap<String, Object>();
		map62.put("countryFlag", R.drawable.lebanon);
		map62.put("countryName", "Lebanon");
		map62.put("countryCode", "+961");
		map62.put("countryId", "118");
			
		list.add(map62);
		
		Map<String, Object> map63=new HashMap<String, Object>();
		map63.put("countryFlag", R.drawable.libya);
		map63.put("countryName", "Libya");
		map63.put("countryCode", "+218");
		map63.put("countryId", "120");
			
		list.add(map63);
		
		Map<String, Object> map64=new HashMap<String, Object>();
		map64.put("countryFlag", R.drawable.malaysia);
		map64.put("countryName", "Malaysia");
		map64.put("countryCode", "+60");
		map64.put("countryId", "129");
		
		list.add(map64);
		
		Map<String, Object> map65=new HashMap<String, Object>();
		map65.put("countryFlag", R.drawable.maldives);
		map65.put("countryName", "Maldives");
		map65.put("countryCode", "+960");
		map65.put("countryId", "130");
			
		list.add(map65);
		

		Map<String, Object> map66=new HashMap<String, Object>();
		map66.put("countryFlag", R.drawable.madagascar);
		map66.put("countryName", "Madagascar");
		map66.put("countryCode", "+261");
		map66.put("countryId", "127");
			
		list.add(map66);
		
		Map<String, Object> map67=new HashMap<String, Object>();
		map67.put("countryFlag", R.drawable.mali);
		map67.put("countryName", "Mali");
		map67.put("countryCode", "+223");
		map67.put("countryId", "131");
			
		list.add(map67);
		
		Map<String, Object> map68=new HashMap<String, Object>();
		map68.put("countryFlag", R.drawable.mauritius);
		map68.put("countryName", "Mauritius");
		map68.put("countryCode", "+230");
		map68.put("countryId", "136");
			
		list.add(map68);
		
		Map<String, Object> map69=new HashMap<String, Object>();
		map69.put("countryFlag", R.drawable.mexico);
		map69.put("countryName", "Mexico");
		map69.put("countryCode", "+52");
		map69.put("countryId", "138");
		
		list.add(map69);
		
		Map<String, Object> map70=new HashMap<String, Object>();
		map70.put("countryFlag", R.drawable.morocco);
		map70.put("countryName", "Morocco");
		map70.put("countryCode", "+212");
		map70.put("countryId", "144");
		
		list.add(map70);
		
		
		Map<String, Object> map71=new HashMap<String, Object>();
		map71.put("countryFlag", R.drawable.namibia);
		map71.put("countryName", "namibia");
		map71.put("countryCode", "+264");
		map71.put("countryId", "147");
		list.add(map71);
		
		Map<String, Object> map72=new HashMap<String, Object>();
		map72.put("countryFlag", R.drawable.nepal);
		map72.put("countryName", "nepal");
		map72.put("countryCode", "+977");
		map72.put("countryId", "149");
		
		list.add(map72);
		
		Map<String, Object> map73=new HashMap<String, Object>();
		map73.put("countryFlag", R.drawable.netherlands);
		map73.put("countryName", "Netherlands");
		map73.put("countryCode", "150");
		map73.put("countryId", "150");
		
		list.add(map73);
		
		Map<String, Object> map74=new HashMap<String, Object>();
		map74.put("countryFlag", R.drawable.newzealand);
		map74.put("countryName", "New Zealand");
		map74.put("countryCode", "+64");
		map74.put("countryId", "153");
		list.add(map74);
		
		Map<String, Object> map75=new HashMap<String, Object>();
		map75.put("countryFlag", R.drawable.niger);
		map75.put("countryName", "Niger");
		map75.put("countryCode", "+227");
		map75.put("countryId", "155");
			
		list.add(map75);
		
		Map<String, Object> map76=new HashMap<String, Object>();
		map76.put("countryFlag", R.drawable.nicaragua);
		map76.put("countryName", "Nicaragua");
		map76.put("countryCode", "+505");
		map76.put("countryId", "154");
			
		list.add(map76);
		
		Map<String, Object> map77=new HashMap<String, Object>();
		map77.put("countryFlag", R.drawable.nigeria);
		map77.put("countryName", "Nigeria");
		map77.put("countryCode", "+234");
		map77.put("countryId", "156");
		
		list.add(map77);
		
		Map<String, Object> map78=new HashMap<String, Object>();
		map78.put("countryFlag", R.drawable.norway);
		map78.put("countryName", "Norway");
		map78.put("countryCode", "160");
		
		list.add(map78);
		
		Map<String, Object> map79=new HashMap<String, Object>();
		map79.put("countryFlag", R.drawable.oman);
		map79.put("countryName", "Oman");
		map79.put("countryCode", "+968");
		map79.put("countryId", "161");
		
		list.add(map79);
		
		Map<String, Object> map80=new HashMap<String, Object>();
		map80.put("countryFlag", R.drawable.pakistan);
		map80.put("countryName", "Pakistan");
		map80.put("countryCode", "+92");
		map80.put("countryId", "162");
		
		list.add(map80);
		
		Map<String, Object> map81=new HashMap<String, Object>();
		map81.put("countryFlag", R.drawable.paraguay);
		map81.put("countryName", "Paraguay");
		map81.put("countryCode", "+595");
		map81.put("countryId", "167");
		
		list.add(map81);
		
		Map<String, Object> map82=new HashMap<String, Object>();
		map82.put("countryFlag", R.drawable.panama);
		map82.put("countryName", "Panama");
		map82.put("countryCode", "+507");
		map82.put("countryId", "165");
		
		list.add(map82);
		
		
		Map<String, Object> map83=new HashMap<String, Object>();
		map83.put("countryFlag", R.drawable.philippines);
		map83.put("countryName", "Philippines");
		map83.put("countryCode", "+63");
		map83.put("countryId", "169");
		
		list.add(map83);
		
		Map<String, Object> map84=new HashMap<String, Object>();
		map84.put("countryFlag", R.drawable.poland);
		map84.put("countryName", "Poland");
		map84.put("countryCode", "+48");
		map84.put("countryId", "171");
		
		list.add(map84);
		
		
		Map<String, Object> map85=new HashMap<String, Object>();
		map85.put("countryFlag", R.drawable.portugal);
		map85.put("countryName", "portugal");
		map85.put("countryCode", "172");
		
		list.add(map85);
		
		Map<String, Object> map86=new HashMap<String, Object>();
		map86.put("countryFlag", R.drawable.puertorico);
		map86.put("countryName", "Puerto Rico");
		map86.put("countryCode", "+1787");
		map86.put("countryId", "173");
			
		list.add(map86);
		
		Map<String, Object> map87=new HashMap<String, Object>();
		map87.put("countryFlag", R.drawable.qatar);
		map87.put("countryName", "Qatar");
		map87.put("countryCode", "+974");
		map87.put("countryId", "174");
		
		list.add(map87);
		
		Map<String, Object> map88=new HashMap<String, Object>();
		map88.put("countryFlag", R.drawable.rwanda);
		map88.put("countryName", "Rwanda");
		map88.put("countryCode", "+250");
		map88.put("countryId", "178");
		
		list.add(map88);
		
		Map<String, Object> map89=new HashMap<String, Object>();
		map89.put("countryFlag", R.drawable.romania);
		map89.put("countryName", "Romania");
		map89.put("countryCode", "+40");
		map89.put("countryId", "176");
		
		list.add(map89);
		
		Map<String, Object> map90=new HashMap<String, Object>();
		map90.put("countryFlag", R.drawable.russia);
		map90.put("countryName", "Russia");
		map90.put("countryCode", "+7");
		map90.put("countryId", "177");
		
		list.add(map90);
		
		Map<String, Object> map91=new HashMap<String, Object>();
		map91.put("countryFlag", R.drawable.saudiarabia);
		map91.put("countryName", "Saudi Arabia");
		map91.put("countryCode", "196");
		
		list.add(map91);
		

		Map<String, Object> map92=new HashMap<String, Object>();
		map92.put("countryFlag", R.drawable.senegal);
		map92.put("countryName", "Senegal");
		map92.put("countryCode", "+221");
		map92.put("countryId", "188");
		
		list.add(map92);
		
		Map<String, Object> map93=new HashMap<String, Object>();
		map93.put("countryFlag", R.drawable.singapore);
		map93.put("countryName", "Singapore");
		map93.put("countryCode", "+65");
		map93.put("countryId", "191");
		
		list.add(map93);
		
		Map<String, Object> map94=new HashMap<String, Object>();
		map94.put("countryFlag", R.drawable.southafrica);
		map94.put("countryName", "South Africa");
		map94.put("countryCode", "+27");
		map94.put("countryId", "196");
		
		list.add(map94);
		
		Map<String, Object> map95=new HashMap<String, Object>();
		map95.put("countryFlag", R.drawable.somalia);
		map95.put("countryName", "Somalia");
		map95.put("countryCode", "+252");
		map95.put("countryId", "195");
			
		list.add(map95);
		
		Map<String, Object> map96=new HashMap<String, Object>();
		map96.put("countryFlag", R.drawable.spain);
		map96.put("countryName", "Spain");
		map96.put("countryCode", "+34");
		map96.put("countryId", "198");
		
		list.add(map96);
		
		
		Map<String, Object> map97=new HashMap<String, Object>();
		map97.put("countryFlag", R.drawable.srilanka);
		map97.put("countryName", "Sri Lanka");
		map97.put("countryCode", "+94");
		map97.put("countryId", "199");
		
		list.add(map97);
		
		Map<String, Object> map98=new HashMap<String, Object>();
		map98.put("countryFlag", R.drawable.sudan);
		map98.put("countryName", "Sudan");
		map98.put("countryCode", "+249");
		map98.put("countryId", "200");
			
		list.add(map98);
		
		Map<String, Object> map99=new HashMap<String, Object>();
		map99.put("countryFlag", R.drawable.sweden);
		map99.put("countryName", "Sweden");
		map99.put("countryCode", "204");
		map99.put("countryId", "204");
		
		list.add(map99);
		
		
		Map<String, Object> map100=new HashMap<String, Object>();
		map100.put("countryFlag", R.drawable.switzerland);
		map100.put("countryName", "Switzerland");
		map100.put("countryCode", "205");
		map100.put("countryId", "205");
		
		list.add(map100);
		
		Map<String, Object> map101=new HashMap<String, Object>();
		map101.put("countryFlag", R.drawable.syria);
		map101.put("countryName", "Syria");
		map101.put("countryCode", "+963");
		map101.put("countryId", "206");
		
		list.add(map101);
		
		Map<String, Object> map102=new HashMap<String, Object>();
		map102.put("countryFlag", R.drawable.tanzania);
		map102.put("countryName", "Tanzania");
		map102.put("countryCode", "+255");
		map102.put("countryId", "209");
		
		list.add(map102);
		
		Map<String, Object> map103=new HashMap<String, Object>();
		map103.put("countryFlag", R.drawable.thailand);
		map103.put("countryName", "Thailand");
		map103.put("countryCode", "+66");
		map103.put("countryId", "210");
		
		list.add(map103);
		
		Map<String, Object> map104=new HashMap<String, Object>();
		map104.put("countryFlag", R.drawable.turkey);
		map104.put("countryName", "Turkey");
		map104.put("countryCode", "+90");
		map104.put("countryId", "217");
		
		list.add(map104);
		
		Map<String, Object> map105=new HashMap<String, Object>();
		map105.put("countryFlag", R.drawable.ukraine);
		map105.put("countryName", "Ukraine");
		map105.put("countryCode", "+280");
		map105.put("countryId", "222");
			
		list.add(map105);
		
		Map<String, Object> map106=new HashMap<String, Object>();
		map106.put("countryFlag", R.drawable.uganda);
		map106.put("countryName", "Uganda");
		map106.put("countryCode", "+256");
		map106.put("countryId", "221");
		
		list.add(map106);
		
		Map<String, Object> map107=new HashMap<String, Object>();
		map107.put("countryFlag", R.drawable.unitedkingdom);
		map107.put("countryName", "United Kingdom");
		map107.put("countryCode", "+44");
		map107.put("countryId", "224");
		
		list.add(map107);
		
		Map<String, Object> map108=new HashMap<String, Object>();
		map108.put("countryFlag", R.drawable.unitedstates);
		map108.put("countryName", "USA");
		map108.put("countryCode", "+1");
		map108.put("countryId", "1");
		
		list.add(map108);
		
		Map<String, Object> map109=new HashMap<String, Object>();
		map109.put("countryFlag", R.drawable.uzbekistan);
		map109.put("countryName", "Uzbekistan");
		map109.put("countryCode", "+998");
		map109.put("countryId", "228");
		
		list.add(map109);
		
		
		
		Map<String, Object> map110=new HashMap<String, Object>();
		map110.put("countryFlag", R.drawable.vietnam);
		map110.put("countryName", "Vietnam");
		map110.put("countryCode", "84");
		map110.put("countryId", "231");
		
		list.add(map110);
		
		Map<String, Object> map111=new HashMap<String, Object>();
		map111.put("countryFlag", R.drawable.venezuela);
		map111.put("countryName", "Venezuela");
		map111.put("countryCode", "+58");
		map111.put("countryId", "230");
			
		list.add(map111);
		
		Map<String, Object> map112=new HashMap<String, Object>();
		map112.put("countryFlag", R.drawable.yemen);
		map112.put("countryName", "Yemen");
		map112.put("countryCode", "+967");
		map112.put("countryId", "236");
		
		list.add(map112);
		
		
		Map<String, Object> map113=new HashMap<String, Object>();
		map113.put("countryFlag", R.drawable.zambia);
		map113.put("countryName", "Zambia");
		map113.put("countryCode", "+260");
		map113.put("countryId", "238");
		
		list.add(map113);
		
		
		Map<String, Object> map114=new HashMap<String, Object>();
		map114.put("countryFlag", R.drawable.zimbabwe);
		map114.put("countryName", "Zimbabwe");
		map114.put("countryCode", "+263");
		map114.put("countryId", "239");
		
		list.add(map114);
		
		
		Map<String, Object> map911=new HashMap<String, Object>();
		map911.put("countryFlag", R.drawable.australia);
		map911.put("countryName", "Antarctica");
		map911.put("countryCode", "8");
		map911.put("countryId", "8");
		
		list.add(map911);
		
		
		System.out.println("LIST "+list.size());
		
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
		
		return list;
	}
	
	



	private void setupSearchView() {
		
		searchView.setIconifiedByDefault(false);
		searchView.setOnQueryTextListener(this);
		searchView.setSubmitButtonEnabled(true);
		searchView.setQueryHint("Search here");
	}

	@Override
	public boolean onQueryTextSubmit(String query)
	{
		
		return false;
	}

	@Override
	public boolean onQueryTextChange(String newText)
	{
		if(TextUtils.isEmpty(newText))
		{
			listview.clearTextFilter();
		}
		else
		{
			listview.setFilterText(newText.toString());
		}
		return true;
	}
	
	
}
