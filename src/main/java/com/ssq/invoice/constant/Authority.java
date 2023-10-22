package com.ssq.invoice.constant;

public class Authority {

    // Group_Admin can CRUD companies and also CRUD all the users of entire group. Group_Admin can also read all information
    public static final String[] GROUP_ADMIN_AUTHORITIES = {
            "companies:read", "spvs:read", "users:read", "mandates:read", "assets:read",
            "company:create", "company:update", "company:delete",
            "user:status_update", "user:delete"};
    // Group_Reader can read all information, target user is internal auditor of the group
    public static final String[] GROUP_READ_AUTHORITIES = {"companies:read", "spvs:read", "users:read", "mandates:read", "assets:read"};

    /**
     *  Company_Admin can update his/her company and also delete mark his/her company. But the final deletion of the company must be confirmed by the group admin.
     *  Company_Admin can create,
     */
    public static final String[] COMPANY_ADMIN_AUTHORITIES = {
            "users:read","spvs:read",  "mandates:read", "assets:read","investors:read",
            "user:status_update", "user:delete", "user:assign_authority",
            "company:update", "company:delete",
            "mandate:create","mandate:update", "mandate:delete",
            "spv:create","spv:update", "spv:delete",
            "asset:create","asset:update", "asset:delete",
            "investor:create","investor:update","investor:delete",
            "customization"
    };

    /**
     * Company_Read can see all information of his/her company. i.e. mandates:read -> can see all mandates, same applies for spvs, assets, users
     */
    public static final String[] COMPANY_READ_AUTHORITIES = {"users:read", "mandates:read", "spvs:read", "assets:read"};

    /**
     * Normal user has basic authority like "company:read", "mandate:read", "spv:read", "asset:read".
     * Since all users have these authorities, it is not necessary to put here.
     */
    public static final String[] NORMAL_USER_AUTHORITIES = {};


}
