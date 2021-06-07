package com.optical.bean;

/**
 * 雷达上送信息接收实体
 * Created by mary on 2021/2/24.
 */
public class RadarEventBean {

    private Long vendor_id;

    private Object unique_id;

    private String imei;

    private String device_code;

    private String device_phone;

    // 时间类型 0：注册时间 1：报警事件 2：消警事件 3：点云数据上传
    private Integer type;

    private Integer confidence;

    private String pointcloud;

    // 上报原因
    // 报警： 0:雷达跌倒报警   1：报警无人应答-超时报警
    // 消警： 0：雷达算法消警 1：按钮主动消警
    private Integer reason;

    //当前帧号
    private Long frame_id;
    //当前事件关联的点云帧的起始帧号
    private Long event_start_frame;
    //当前事件关联的点云帧的结束帧号
    private Long event_end_frame;
    //当前高度
    private Double height_curr;
    //事件发生前高度
    private Double height_before;

    private Double leave_ground_duration;

    private Double on_ground_duration;

    private Double confidence_threshold;

    private Double sensor_fix_height;

    private String cur_frame_num;


    public String getCur_frame_num() {
        return cur_frame_num;
    }

    public void setCur_frame_num(String cur_frame_num) {
        this.cur_frame_num = cur_frame_num;
    }

    public Object getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(Object unique_id) {
        this.unique_id = unique_id;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getDevice_code() {
        return device_code;
    }

    public void setDevice_code(String device_code) {
        this.device_code = device_code;
    }



    public Long getFrame_id() {
        return frame_id;
    }

    public void setFrame_id(Long frame_id) {
        this.frame_id = frame_id;
    }

    public Long getEvent_start_frame() {
        return event_start_frame;
    }

    public void setEvent_start_frame(Long event_start_frame) {
        this.event_start_frame = event_start_frame;
    }

    public Long getEvent_end_frame() {
        return event_end_frame;
    }

    public void setEvent_end_frame(Long event_end_frame) {
        this.event_end_frame = event_end_frame;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getConfidence() {
        return confidence;
    }

    public void setConfidence(Integer confidence) {
        this.confidence = confidence;
    }

    public Integer getReason() {
        return reason;
    }

    public void setReason(Integer reason) {
        this.reason = reason;
    }

    public Double getHeight_curr() {
        return height_curr;
    }

    public void setHeight_curr(Double height_curr) {
        this.height_curr = height_curr;
    }

    public Double getHeight_before() {
        return height_before;
    }

    public void setHeight_before(Double height_before) {
        this.height_before = height_before;
    }

    public String getDevice_phone() {
        return device_phone;
    }

    public void setDevice_phone(String device_phone) {
        this.device_phone = device_phone;
    }

    public Double getLeave_ground_duration() {
        return leave_ground_duration;
    }

    public void setLeave_ground_duration(Double leave_ground_duration) {
        this.leave_ground_duration = leave_ground_duration;
    }

    public Double getOn_ground_duration() {
        return on_ground_duration;
    }

    public void setOn_ground_duration(Double on_ground_duration) {
        this.on_ground_duration = on_ground_duration;
    }

    public Double getConfidence_threshold() {
        return confidence_threshold;
    }

    public void setConfidence_threshold(Double confidence_threshold) {
        this.confidence_threshold = confidence_threshold;
    }

    public String getPointcloud() {
        return pointcloud;
    }

    public void setPointcloud(String pointcloud) {
        this.pointcloud = pointcloud;
    }

    public Double getSensor_fix_height() {
        return sensor_fix_height;
    }

    public void setSensor_fix_height(Double sensor_fix_height) {
        this.sensor_fix_height = sensor_fix_height;
    }
}
