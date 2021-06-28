package com.optical.Service;

import com.optical.bean.OpWebResult;
import com.optical.bean.ResidentInfo;

/**
 * Created by mary on 2021/4/14.
 */
public interface ResidentService {

    public OpWebResult getResidentList(Integer floor, String roomName, Integer page, Integer limit);

    public OpWebResult getFloor();

    public OpWebResult getFloorRoom(Integer floor, String deviceCode);

    public OpWebResult getResidentInfoById(Long id);

    public OpWebResult addNewResident(ResidentInfo residentInfo);

    public OpWebResult updateResident(Long id, String roomName, Integer floor, Integer age, Integer gender,
                                      String description, String name, String urgentPhone);

    public OpWebResult removeResident(Long id);

    public OpWebResult getTabs(Long vendorId);

}
