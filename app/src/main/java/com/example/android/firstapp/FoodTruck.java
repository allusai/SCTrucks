package com.example.android.firstapp;

import java.util.HashMap;
public class FoodTruck
{
	private String name, cuisine, website, image;
	private double rating, latitude, longitude, distance;
	private HashMap<String, String[]> hours;
	public FoodTruck(String n, double lat, double longit, double rat, double dist, String cuis, String site, String img)
	{
		name = n;
		latitude = lat;
		longitude = longit;
		rating = rat;
		distance = dist;
		cuisine = cuis;
		website = site;
		if(img == null)
		{
			image = "https://larkinsquare.com/app/uploads/2016/06/lomo-lomo-food-truck.png";
		}
		else
		{
			image = img;
		}

		hours = new HashMap<String,String[]>();
		hours.put("Monday", new String[] {"9999","9999"});
		hours.put("Tuesday", new String[] {"9999","9999"});
		hours.put("Wednesday", new String[] {"9999","9999"});
		hours.put("Thursday", new String[] {"9999","9999"});
		hours.put("Friday", new String[] {"9999","9999"});
		hours.put("Saturday", new String[] {"9999","9999"});
		hours.put("Sunday", new String[] {"9999","9999"});
	}
	public FoodTruck(String n, double lat, double longit)
	{
		name = n;
		latitude = lat;
		longitude = longit;
	}
	public String getName()
	{
		return name;
	}
	public String getCuisine()
	{
		return cuisine;
	}
	public String getWebsite()
	{
		return website;
	}
	public double getRating()
	{
		return rating;
	}
	public HashMap<String,String[]> getHours()
	{
		return hours;
	}
	public String getImage()
	{
		return image;
	}
	public double getDistance()
	{
		return distance;
	}
	public double getLatitude()
	{
		return latitude;
	}
	public double getLongitude()
	{
		return longitude;
	}
	public void setName(String n)
	{
		name = n;
	}
	public void setRating(double r)
	{
		rating = r;
	}
	public void setCuisine(String c)
	{
		cuisine = c;
	}
	public void setWebsite(String w)
	{
		website = w;
	}
	public void setLongitude(double l)
	{
		longitude = l;
	}
	public void setLatitude(double l)
	{
		latitude = l;
	}
	public void setDistance(double d)
	{
		distance = d;
	}
	public void addKey(String key, String[] h)
	{
		hours.put(key,h);
	}
	public void setHours(HashMap<String,String[]> h)
	{
		hours = h;
	}
	public void setImage(String i)
	{
		image = i;
	}
	public String toString()
	{
		String s = name + "   " + rating + "   " + cuisine + "\t";
		return s;
	}
}