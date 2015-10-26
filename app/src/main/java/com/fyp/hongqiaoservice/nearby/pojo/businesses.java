package com.fyp.hongqiaoservice.nearby.pojo;

import java.util.List;

/**
 * Created by Administrator on 2015/2/17.
 */
public class businesses {
    private String business_id;

    private String name;

    private String branch_name;

    private String address;

    private String telephone;

    private String city;

    private List<String> regions ;

    private List<String> categories ;

    private String latitude;

    private String longitude;

    private float avg_rating;

    private String rating_img_url;

    private String rating_s_img_url;

    private String product_grade;

    private String decoration_grade;

    private String service_grade;

    private String product_score;

    private String decoration_score;

    private String service_score;

    private String avg_price;

    private String review_count;

    private String review_list_url;

    private String distance;

    private String business_url;

    private String photo_url;

    private String s_photo_url;

    private String photo_count;

    private String photo_list_url;

    private String has_coupon;

    private String coupon_id;

    private String coupon_description;

    private String coupon_url;

    private String has_deal;

    private String deal_count;

    private List<com.fyp.hongqiaoservice.nearby.pojo.deals> deals ;

    private String has_online_reservation;

    private String online_reservation_url;

    public businesses() {
    }

    public businesses(String business_id, String name, String branch_name, String address, String telephone, String city, List<String> regions, List<String> categories, String latitude, String longitude, float avg_rating, String rating_img_url, String rating_s_img_url, String product_grade, String decoration_grade, String service_grade, String product_score, String decoration_score, String service_score, String avg_price, String review_count, String review_list_url, String distance, String business_url, String photo_url, String s_photo_url, String photo_count, String photo_list_url, String has_coupon, String coupon_id, String coupon_description, String coupon_url, String has_deal, String deal_count, List<com.fyp.hongqiaoservice.nearby.pojo.deals> deals, String has_online_reservation, String online_reservation_url) {
        this.business_id = business_id;
        this.name = name;
        this.branch_name = branch_name;
        this.address = address;
        this.telephone = telephone;
        this.city = city;
        this.regions = regions;
        this.categories = categories;
        this.latitude = latitude;
        this.longitude = longitude;
        this.avg_rating = avg_rating;
        this.rating_img_url = rating_img_url;
        this.rating_s_img_url = rating_s_img_url;
        this.product_grade = product_grade;
        this.decoration_grade = decoration_grade;
        this.service_grade = service_grade;
        this.product_score = product_score;
        this.decoration_score = decoration_score;
        this.service_score = service_score;
        this.avg_price = avg_price;
        this.review_count = review_count;
        this.review_list_url = review_list_url;
        this.distance = distance;
        this.business_url = business_url;
        this.photo_url = photo_url;
        this.s_photo_url = s_photo_url;
        this.photo_count = photo_count;
        this.photo_list_url = photo_list_url;
        this.has_coupon = has_coupon;
        this.coupon_id = coupon_id;
        this.coupon_description = coupon_description;
        this.coupon_url = coupon_url;
        this.has_deal = has_deal;
        this.deal_count = deal_count;
        this.deals = deals;
        this.has_online_reservation = has_online_reservation;
        this.online_reservation_url = online_reservation_url;
    }

    @Override
    public String toString() {
        return "Businesses{" +
                "business_id='" + business_id + '\'' +
                ", name='" + name + '\'' +
                ", branch_name='" + branch_name + '\'' +
                ", address='" + address + '\'' +
                ", telephone='" + telephone + '\'' +
                ", city='" + city + '\'' +
                ", regions=" + regions +
                ", categories=" + categories +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", avg_rating='" + avg_rating + '\'' +
                ", rating_img_url='" + rating_img_url + '\'' +
                ", rating_s_img_url='" + rating_s_img_url + '\'' +
                ", product_grade='" + product_grade + '\'' +
                ", decoration_grade='" + decoration_grade + '\'' +
                ", service_grade='" + service_grade + '\'' +
                ", product_score='" + product_score + '\'' +
                ", decoration_score='" + decoration_score + '\'' +
                ", service_score='" + service_score + '\'' +
                ", avg_price='" + avg_price + '\'' +
                ", review_count='" + review_count + '\'' +
                ", review_list_url='" + review_list_url + '\'' +
                ", distance='" + distance + '\'' +
                ", business_url='" + business_url + '\'' +
                ", photo_url='" + photo_url + '\'' +
                ", s_photo_url='" + s_photo_url + '\'' +
                ", photo_count='" + photo_count + '\'' +
                ", photo_list_url='" + photo_list_url + '\'' +
                ", has_coupon='" + has_coupon + '\'' +
                ", coupon_id='" + coupon_id + '\'' +
                ", coupon_description='" + coupon_description + '\'' +
                ", coupon_url='" + coupon_url + '\'' +
                ", has_deal='" + has_deal + '\'' +
                ", deal_count='" + deal_count + '\'' +
                ", deals=" + deals +
                ", has_online_reservation='" + has_online_reservation + '\'' +
                ", online_reservation_url='" + online_reservation_url + '\'' +
                '}';
    }

    public String getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(String business_id) {
        this.business_id = business_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<String> getRegions() {
        return regions;
    }

    public void setRegions(List<String> regions) {
        this.regions = regions;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public float getAvg_rating() {
        return avg_rating;
    }

    public void setAvg_rating(float avg_rating) {
        this.avg_rating = avg_rating;
    }

    public String getRating_img_url() {
        return rating_img_url;
    }

    public void setRating_img_url(String rating_img_url) {
        this.rating_img_url = rating_img_url;
    }

    public String getRating_s_img_url() {
        return rating_s_img_url;
    }

    public void setRating_s_img_url(String rating_s_img_url) {
        this.rating_s_img_url = rating_s_img_url;
    }

    public String getProduct_grade() {
        return product_grade;
    }

    public void setProduct_grade(String product_grade) {
        this.product_grade = product_grade;
    }

    public String getDecoration_grade() {
        return decoration_grade;
    }

    public void setDecoration_grade(String decoration_grade) {
        this.decoration_grade = decoration_grade;
    }

    public String getService_grade() {
        return service_grade;
    }

    public void setService_grade(String service_grade) {
        this.service_grade = service_grade;
    }

    public String getProduct_score() {
        return product_score;
    }

    public void setProduct_score(String product_score) {
        this.product_score = product_score;
    }

    public String getDecoration_score() {
        return decoration_score;
    }

    public void setDecoration_score(String decoration_score) {
        this.decoration_score = decoration_score;
    }

    public String getService_score() {
        return service_score;
    }

    public void setService_score(String service_score) {
        this.service_score = service_score;
    }

    public String getAvg_price() {
        return avg_price;
    }

    public void setAvg_price(String avg_price) {
        this.avg_price = avg_price;
    }

    public String getReview_count() {
        return review_count;
    }

    public void setReview_count(String review_count) {
        this.review_count = review_count;
    }

    public String getReview_list_url() {
        return review_list_url;
    }

    public void setReview_list_url(String review_list_url) {
        this.review_list_url = review_list_url;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getBusiness_url() {
        return business_url;
    }

    public void setBusiness_url(String business_url) {
        this.business_url = business_url;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public String getS_photo_url() {
        return s_photo_url;
    }

    public void setS_photo_url(String s_photo_url) {
        this.s_photo_url = s_photo_url;
    }

    public String getPhoto_count() {
        return photo_count;
    }

    public void setPhoto_count(String photo_count) {
        this.photo_count = photo_count;
    }

    public String getPhoto_list_url() {
        return photo_list_url;
    }

    public void setPhoto_list_url(String photo_list_url) {
        this.photo_list_url = photo_list_url;
    }

    public String getHas_coupon() {
        return has_coupon;
    }

    public void setHas_coupon(String has_coupon) {
        this.has_coupon = has_coupon;
    }

    public String getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(String coupon_id) {
        this.coupon_id = coupon_id;
    }

    public String getCoupon_description() {
        return coupon_description;
    }

    public void setCoupon_description(String coupon_description) {
        this.coupon_description = coupon_description;
    }

    public String getCoupon_url() {
        return coupon_url;
    }

    public void setCoupon_url(String coupon_url) {
        this.coupon_url = coupon_url;
    }

    public String getHas_deal() {
        return has_deal;
    }

    public void setHas_deal(String has_deal) {
        this.has_deal = has_deal;
    }

    public String getDeal_count() {
        return deal_count;
    }

    public void setDeal_count(String deal_count) {
        this.deal_count = deal_count;
    }

    public List<deals> getDeals() {
        return deals;
    }

    public void setDeals(List<deals> deals) {
        this.deals = deals;
    }

    public String getHas_online_reservation() {
        return has_online_reservation;
    }

    public void setHas_online_reservation(String has_online_reservation) {
        this.has_online_reservation = has_online_reservation;
    }

    public String getOnline_reservation_url() {
        return online_reservation_url;
    }

    public void setOnline_reservation_url(String online_reservation_url) {
        this.online_reservation_url = online_reservation_url;
    }
}
