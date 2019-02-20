package com.ys.administrator.mydemo.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class ProjectInfoBean extends BaseBean {

    /**
     * project : {"at":1.550550410045E12,"data":{"其他资料":{"其他资料":[]},"图审上报资料":{"土建项目":[],"装修工程":[]},"图审合格资料":{"合格报告":[],"图纸":[]},"土建图纸":{"暖通":[],"电气":[],"结构（含计算机书）":[],"给排水":[],"节能 (含设计表、自评表)":[],"装饰":[]},"工程基本信息":{"营业执照名称或预核名":[]},"装修图纸":{"暖通":[],"电气":[],"结构（含计算机书）":[],"给排水":[],"装饰":[]},"装修项目基础资料":{"原始建筑图纸":[],"建筑功能改变文件":[],"建筑合法性证明文件":[],"建筑消防验收意见书":[],"现场照片或视频":[],"租赁合同":[]}},"id":84,"info":{"功能":"11111","地址":"33333","建筑面积":"22222","电话":"1234567890","联系人":"test57"},"name":"test57","status":1,"type":1}
     */

    private ProjectBean project;

    public ProjectBean getProject() {
        return project;
    }

    public void setProject(ProjectBean project) {
        this.project = project;
    }

    public static class ProjectBean {
        /**
         * at : 1.550550410045E12
         * data : {"其他资料":{"其他资料":[]},"图审上报资料":{"土建项目":[],"装修工程":[]},"图审合格资料":{"合格报告":[],"图纸":[]},"土建图纸":{"暖通":[],"电气":[],"结构（含计算机书）":[],"给排水":[],"节能 (含设计表、自评表)":[],"装饰":[]},"工程基本信息":{"营业执照名称或预核名":[]},"装修图纸":{"暖通":[],"电气":[],"结构（含计算机书）":[],"给排水":[],"装饰":[]},"装修项目基础资料":{"原始建筑图纸":[],"建筑功能改变文件":[],"建筑合法性证明文件":[],"建筑消防验收意见书":[],"现场照片或视频":[],"租赁合同":[]}}
         * id : 84
         * info : {"功能":"11111","地址":"33333","建筑面积":"22222","电话":"1234567890","联系人":"test57"}
         * name : test57
         * status : 1
         * type : 1
         */

        private double at;
        private Map<String,Object> data;
        private int id;
        private InfoBean info;
        private String name;
        private int status;
        private int type;

        public double getAt() {
            return at;
        }

        public void setAt(double at) {
            this.at = at;
        }

        public Map<String,Object> getData() {
            return data;
        }

        public void setData(Map<String,Object> data) {
            this.data = data;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

//        public static class DataBean {
//            /**
//             * 其他资料 : {"其他资料":[]}
//             * 图审上报资料 : {"土建项目":[],"装修工程":[]}
//             * 图审合格资料 : {"合格报告":[],"图纸":[]}
//             * 土建图纸 : {"暖通":[],"电气":[],"结构（含计算机书）":[],"给排水":[],"节能 (含设计表、自评表)":[],"装饰":[]}
//             * 工程基本信息 : {"营业执照名称或预核名":[]}
//             * 装修图纸 : {"暖通":[],"电气":[],"结构（含计算机书）":[],"给排水":[],"装饰":[]}
//             * 装修项目基础资料 : {"原始建筑图纸":[],"建筑功能改变文件":[],"建筑合法性证明文件":[],"建筑消防验收意见书":[],"现场照片或视频":[],"租赁合同":[]}
//             */
//
//            private 其他资料Bean 其他资料;
//            private 图审上报资料Bean 图审上报资料;
//            private 图审合格资料Bean 图审合格资料;
//            private 土建图纸Bean 土建图纸;
//            private 工程基本信息Bean 工程基本信息;
//            private 装修图纸Bean 装修图纸;
//            private 装修项目基础资料Bean 装修项目基础资料;
//
//            public 其他资料Bean get其他资料() {
//                return 其他资料;
//            }
//
//            public void set其他资料(其他资料Bean 其他资料) {
//                this.其他资料 = 其他资料;
//            }
//
//            public 图审上报资料Bean get图审上报资料() {
//                return 图审上报资料;
//            }
//
//            public void set图审上报资料(图审上报资料Bean 图审上报资料) {
//                this.图审上报资料 = 图审上报资料;
//            }
//
//            public 图审合格资料Bean get图审合格资料() {
//                return 图审合格资料;
//            }
//
//            public void set图审合格资料(图审合格资料Bean 图审合格资料) {
//                this.图审合格资料 = 图审合格资料;
//            }
//
//            public 土建图纸Bean get土建图纸() {
//                return 土建图纸;
//            }
//
//            public void set土建图纸(土建图纸Bean 土建图纸) {
//                this.土建图纸 = 土建图纸;
//            }
//
//            public 工程基本信息Bean get工程基本信息() {
//                return 工程基本信息;
//            }
//
//            public void set工程基本信息(工程基本信息Bean 工程基本信息) {
//                this.工程基本信息 = 工程基本信息;
//            }
//
//            public 装修图纸Bean get装修图纸() {
//                return 装修图纸;
//            }
//
//            public void set装修图纸(装修图纸Bean 装修图纸) {
//                this.装修图纸 = 装修图纸;
//            }
//
//            public 装修项目基础资料Bean get装修项目基础资料() {
//                return 装修项目基础资料;
//            }
//
//            public void set装修项目基础资料(装修项目基础资料Bean 装修项目基础资料) {
//                this.装修项目基础资料 = 装修项目基础资料;
//            }
//
//            public static class 其他资料Bean {
//                private List<?> 其他资料;
//
//                public List<?> get其他资料() {
//                    return 其他资料;
//                }
//
//                public void set其他资料(List<?> 其他资料) {
//                    this.其他资料 = 其他资料;
//                }
//            }
//
//            public static class 图审上报资料Bean {
//                private List<?> 土建项目;
//                private List<?> 装修工程;
//
//                public List<?> get土建项目() {
//                    return 土建项目;
//                }
//
//                public void set土建项目(List<?> 土建项目) {
//                    this.土建项目 = 土建项目;
//                }
//
//                public List<?> get装修工程() {
//                    return 装修工程;
//                }
//
//                public void set装修工程(List<?> 装修工程) {
//                    this.装修工程 = 装修工程;
//                }
//            }
//
//            public static class 图审合格资料Bean {
//                private List<?> 合格报告;
//                private List<?> 图纸;
//
//                public List<?> get合格报告() {
//                    return 合格报告;
//                }
//
//                public void set合格报告(List<?> 合格报告) {
//                    this.合格报告 = 合格报告;
//                }
//
//                public List<?> get图纸() {
//                    return 图纸;
//                }
//
//                public void set图纸(List<?> 图纸) {
//                    this.图纸 = 图纸;
//                }
//            }
//
//            public static class 土建图纸Bean {
//                private List<?> 暖通;
//                private List<?> 电气;
//                @SerializedName("结构（含计算机书）")
//                private List<?> _$314; // FIXME check this code
//                private List<?> 给排水;
//                @SerializedName("节能 (含设计表、自评表)")
//                private List<?> _$267; // FIXME check this code
//                private List<?> 装饰;
//
//                public List<?> get暖通() {
//                    return 暖通;
//                }
//
//                public void set暖通(List<?> 暖通) {
//                    this.暖通 = 暖通;
//                }
//
//                public List<?> get电气() {
//                    return 电气;
//                }
//
//                public void set电气(List<?> 电气) {
//                    this.电气 = 电气;
//                }
//
//                public List<?> get_$314() {
//                    return _$314;
//                }
//
//                public void set_$314(List<?> _$314) {
//                    this._$314 = _$314;
//                }
//
//                public List<?> get给排水() {
//                    return 给排水;
//                }
//
//                public void set给排水(List<?> 给排水) {
//                    this.给排水 = 给排水;
//                }
//
//                public List<?> get_$267() {
//                    return _$267;
//                }
//
//                public void set_$267(List<?> _$267) {
//                    this._$267 = _$267;
//                }
//
//                public List<?> get装饰() {
//                    return 装饰;
//                }
//
//                public void set装饰(List<?> 装饰) {
//                    this.装饰 = 装饰;
//                }
//            }
//
//            public static class 工程基本信息Bean {
//                private List<?> 营业执照名称或预核名;
//
//                public List<?> get营业执照名称或预核名() {
//                    return 营业执照名称或预核名;
//                }
//
//                public void set营业执照名称或预核名(List<?> 营业执照名称或预核名) {
//                    this.营业执照名称或预核名 = 营业执照名称或预核名;
//                }
//            }
//
//            public static class 装修图纸Bean {
//                private List<?> 暖通;
//                private List<?> 电气;
//                @SerializedName("结构（含计算机书）")
//                private List<?> _$165; // FIXME check this code
//                private List<?> 给排水;
//                private List<?> 装饰;
//
//                public List<?> get暖通() {
//                    return 暖通;
//                }
//
//                public void set暖通(List<?> 暖通) {
//                    this.暖通 = 暖通;
//                }
//
//                public List<?> get电气() {
//                    return 电气;
//                }
//
//                public void set电气(List<?> 电气) {
//                    this.电气 = 电气;
//                }
//
//                public List<?> get_$165() {
//                    return _$165;
//                }
//
//                public void set_$165(List<?> _$165) {
//                    this._$165 = _$165;
//                }
//
//                public List<?> get给排水() {
//                    return 给排水;
//                }
//
//                public void set给排水(List<?> 给排水) {
//                    this.给排水 = 给排水;
//                }
//
//                public List<?> get装饰() {
//                    return 装饰;
//                }
//
//                public void set装饰(List<?> 装饰) {
//                    this.装饰 = 装饰;
//                }
//            }
//
//            public static class 装修项目基础资料Bean {
//                private List<?> 原始建筑图纸;
//                private List<?> 建筑功能改变文件;
//                private List<?> 建筑合法性证明文件;
//                private List<?> 建筑消防验收意见书;
//                private List<?> 现场照片或视频;
//                private List<?> 租赁合同;
//
//                public List<?> get原始建筑图纸() {
//                    return 原始建筑图纸;
//                }
//
//                public void set原始建筑图纸(List<?> 原始建筑图纸) {
//                    this.原始建筑图纸 = 原始建筑图纸;
//                }
//
//                public List<?> get建筑功能改变文件() {
//                    return 建筑功能改变文件;
//                }
//
//                public void set建筑功能改变文件(List<?> 建筑功能改变文件) {
//                    this.建筑功能改变文件 = 建筑功能改变文件;
//                }
//
//                public List<?> get建筑合法性证明文件() {
//                    return 建筑合法性证明文件;
//                }
//
//                public void set建筑合法性证明文件(List<?> 建筑合法性证明文件) {
//                    this.建筑合法性证明文件 = 建筑合法性证明文件;
//                }
//
//                public List<?> get建筑消防验收意见书() {
//                    return 建筑消防验收意见书;
//                }
//
//                public void set建筑消防验收意见书(List<?> 建筑消防验收意见书) {
//                    this.建筑消防验收意见书 = 建筑消防验收意见书;
//                }
//
//                public List<?> get现场照片或视频() {
//                    return 现场照片或视频;
//                }
//
//                public void set现场照片或视频(List<?> 现场照片或视频) {
//                    this.现场照片或视频 = 现场照片或视频;
//                }
//
//                public List<?> get租赁合同() {
//                    return 租赁合同;
//                }
//
//                public void set租赁合同(List<?> 租赁合同) {
//                    this.租赁合同 = 租赁合同;
//                }
//            }
//        }

        public static class InfoBean {
            /**
             * 功能 : 11111
             * 地址 : 33333
             * 建筑面积 : 22222
             * 电话 : 1234567890
             * 联系人 : test57
             */

            private String 功能;
            private String 地址;
            private String 建筑面积;
            private String 电话;
            private String 联系人;

            public String get功能() {
                return 功能;
            }

            public void set功能(String 功能) {
                this.功能 = 功能;
            }

            public String get地址() {
                return 地址;
            }

            public void set地址(String 地址) {
                this.地址 = 地址;
            }

            public String get建筑面积() {
                return 建筑面积;
            }

            public void set建筑面积(String 建筑面积) {
                this.建筑面积 = 建筑面积;
            }

            public String get电话() {
                return 电话;
            }

            public void set电话(String 电话) {
                this.电话 = 电话;
            }

            public String get联系人() {
                return 联系人;
            }

            public void set联系人(String 联系人) {
                this.联系人 = 联系人;
            }
        }
    }
}
