package cn.vastsky.onlineshop.installment.model.bean.response;

import java.util.List;

import cn.vastsky.libs.common.base.bean.BaseBean;


/**
 * @author: kezy
 * @create_time 2019/11/13
 * @description:
 */
public class HomePageInfoResponse extends BaseBean {


    /**
     * category : [{"id":1,"keywords":"服装","level":0,"name":"服装","navStatus":1,"parentId":0,"productCount":100,"productUnit":"件","showStatus":1,"sort":1},{"id":2,"keywords":"手机数码","level":0,"name":"手机数码","navStatus":1,"parentId":0,"productCount":100,"productUnit":"件","showStatus":1,"sort":1},{"icon":"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20190129/subject_cate_jiadian.png","id":3,"keywords":"家用电器","level":0,"name":"家用电器","navStatus":1,"parentId":0,"productCount":100,"productUnit":"件","showStatus":1,"sort":1},{"id":4,"keywords":"家具家装","level":0,"name":"家具家装","navStatus":1,"parentId":0,"productCount":100,"productUnit":"件","showStatus":1,"sort":1},{"id":5,"keywords":"汽车用品","level":0,"name":"汽车用品","navStatus":1,"parentId":0,"productCount":100,"productUnit":"件","showStatus":1,"sort":1}]
     * position : {"adBroadcast":[{"adImage":"","adText":"限时福利大优惠, 分享邀请赢免息分期特权","adUrl":"https://www.baidu.com/","id":3,"positionId":2,"seq":1}],"adIcon":[{"adImage":"https://img.iplaysoft.com/wp-content/uploads/2019/free-images/free_stock_photo.jpg","adText":"福利","adUrl":"https://www.baidu.com/","id":1,"positionId":1,"seq":1},{"adImage":"https://img.iplaysoft.com/wp-content/uploads/2019/free-images/free_stock_photo.jpg","adText":"闪分期","adUrl":"https://www.baidu.com/","id":2,"positionId":1,"seq":2}],"adMain":[{"adImage":"https://img.iplaysoft.com/wp-content/uploads/2019/free-images/free_stock_photo.jpg","adText":"便利分期单笔最高额度3000","adUrl":"https://www.baidu.com/","id":4,"positionId":3,"seq":1},{"adImage":"https://img.iplaysoft.com/wp-content/uploads/2019/free-images/free_stock_photo.jpg","adText":"签到送礼","adUrl":"https://www.baidu.com/","id":5,"positionId":3,"seq":2}],"adSub1":[{"adImage":"https://img.iplaysoft.com/wp-content/uploads/2019/free-images/free_stock_photo.jpg","adText":"甄选限时购，全场免息GOGOGO","adUrl":"https://www.baidu.com/","id":6,"positionId":4,"seq":0}]}
     * subject : {"id":16,"product":[{"id":26,"keywords":[],"name":"华为 HUAWEI P20 ","originalPrice":4288,"pic":"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/5ac1bf58Ndefaac16.jpg","price":3788,"productSn":"6946605","stock":1000,"storeId":0,"unit":"件"},{"id":27,"keywords":[],"name":"小米8 全面屏游戏智能手机 6GB+64GB 黑色 全网通4G 双卡双待","originalPrice":2699,"pic":"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/xiaomi.jpg","price":2699,"productSn":"7437788","stock":100,"storeId":0,"unit":""},{"id":28,"keywords":[],"name":"小米 红米5A 全网通版 3GB+32GB 香槟金 移动联通电信4G手机 双卡双待","originalPrice":649,"pic":"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/5a9d248cN071f4959.jpg","price":649,"productSn":"7437789","stock":100,"storeId":0,"unit":""},{"id":29,"keywords":[],"name":"Apple iPhone 8 Plus 64GB 红色特别版 移动联通电信4G手机","originalPrice":5499,"pic":"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/5acc5248N6a5f81cd.jpg","price":5499,"productSn":"7437799","stock":100,"storeId":0,"unit":""},{"id":30,"keywords":[],"name":"HLA海澜之家简约动物印花短袖T恤","originalPrice":98,"pic":"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/5ad83a4fN6ff67ecd.jpg!cc_350x449.jpg","price":98,"productSn":"HNTBJ2E042A","stock":100,"storeId":0,"unit":""},{"id":31,"keywords":[],"name":"HLA海澜之家蓝灰花纹圆领针织布短袖T恤","originalPrice":98,"pic":"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/5ac98b64N70acd82f.jpg!cc_350x449.jpg","price":98,"productSn":"HNTBJ2E080A","stock":100,"storeId":0,"unit":""},{"id":35,"keywords":[],"name":"耐克NIKE 男子 休闲鞋 ROSHE RUN 运动鞋 511881-010黑色41码","originalPrice":369,"pic":"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/5b235bb9Nf606460b.jpg","price":369,"productSn":"6799342","stock":100,"storeId":0,"unit":""},{"id":36,"keywords":[],"name":"耐克NIKE 男子 气垫 休闲鞋 AIR MAX 90 ESSENTIAL 运动鞋 AJ1285-101白色41码","originalPrice":499,"pic":"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/5b19403eN9f0b3cb8.jpg","price":499,"productSn":"6799345","stock":100,"storeId":0,"unit":""},{"id":32,"keywords":[],"name":"HLA海澜之家短袖T恤男基础款","originalPrice":68,"pic":"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/5a51eb88Na4797877.jpg","price":68,"productSn":"HNTBJ2E153A","stock":100,"storeId":0,"unit":""},{"id":33,"keywords":[],"name":"小米（MI）小米电视4A ","originalPrice":2499,"pic":"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/5b02804dN66004d73.jpg","price":2499,"productSn":"4609652","stock":100,"storeId":0,"unit":""},{"id":34,"keywords":[],"name":"小米（MI）小米电视4A 65英寸","originalPrice":3999,"pic":"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/5b028530N51eee7d4.jpg","price":3999,"productSn":"4609660","stock":100,"storeId":0,"unit":""}],"title":"超值热卖"}
     */

    public PositionBean position;
    public SubjectBean subject;
    public List<CategoryBean> category;

    public PositionBean getPosition() {
        return position;
    }

    public void setPosition(PositionBean position) {
        this.position = position;
    }

    public SubjectBean getSubject() {
        return subject;
    }

    public void setSubject(SubjectBean subject) {
        this.subject = subject;
    }

    public List<CategoryBean> getCategory() {
        return category;
    }

    public void setCategory(List<CategoryBean> category) {
        this.category = category;
    }

    public static class PositionBean {
        public List<AdBroadcastBean> adBroadcast;
        public List<AdIconBean> adIcon;
        public List<AdMainBean> adMain;
        public List<AdSub1Bean> adSub1;

        public List<AdBroadcastBean> getAdBroadcast() {
            return adBroadcast;
        }

        public void setAdBroadcast(List<AdBroadcastBean> adBroadcast) {
            this.adBroadcast = adBroadcast;
        }

        public List<AdIconBean> getAdIcon() {
            return adIcon;
        }

        public void setAdIcon(List<AdIconBean> adIcon) {
            this.adIcon = adIcon;
        }

        public List<AdMainBean> getAdMain() {
            return adMain;
        }

        public void setAdMain(List<AdMainBean> adMain) {
            this.adMain = adMain;
        }

        public List<AdSub1Bean> getAdSub1() {
            return adSub1;
        }

        public void setAdSub1(List<AdSub1Bean> adSub1) {
            this.adSub1 = adSub1;
        }

        public static class AdBroadcastBean {
            /**
             * adImage :
             * adText : 限时福利大优惠, 分享邀请赢免息分期特权
             * adUrl : https://www.baidu.com/
             * id : 3
             * positionId : 2
             * seq : 1
             */

            public String adImage;
            public String adText;
            public String adUrl;
            public int id;
            public int positionId;
            public int seq;

            public String getAdImage() {
                return adImage;
            }

            public void setAdImage(String adImage) {
                this.adImage = adImage;
            }

            public String getAdText() {
                return adText;
            }

            public void setAdText(String adText) {
                this.adText = adText;
            }

            public String getAdUrl() {
                return adUrl;
            }

            public void setAdUrl(String adUrl) {
                this.adUrl = adUrl;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getPositionId() {
                return positionId;
            }

            public void setPositionId(int positionId) {
                this.positionId = positionId;
            }

            public int getSeq() {
                return seq;
            }

            public void setSeq(int seq) {
                this.seq = seq;
            }
        }

        public static class AdIconBean {
            /**
             * adImage : https://img.iplaysoft.com/wp-content/uploads/2019/free-images/free_stock_photo.jpg
             * adText : 福利
             * adUrl : https://www.baidu.com/
             * id : 1
             * positionId : 1
             * seq : 1
             */

            public String adImage;
            public String adText;
            public String adUrl;
            public int id;
            public int positionId;
            public int seq;

            public String getAdImage() {
                return adImage;
            }

            public void setAdImage(String adImage) {
                this.adImage = adImage;
            }

            public String getAdText() {
                return adText;
            }

            public void setAdText(String adText) {
                this.adText = adText;
            }

            public String getAdUrl() {
                return adUrl;
            }

            public void setAdUrl(String adUrl) {
                this.adUrl = adUrl;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getPositionId() {
                return positionId;
            }

            public void setPositionId(int positionId) {
                this.positionId = positionId;
            }

            public int getSeq() {
                return seq;
            }

            public void setSeq(int seq) {
                this.seq = seq;
            }
        }

        public static class AdMainBean {
            /**
             * adImage : https://img.iplaysoft.com/wp-content/uploads/2019/free-images/free_stock_photo.jpg
             * adText : 便利分期单笔最高额度3000
             * adUrl : https://www.baidu.com/
             * id : 4
             * positionId : 3
             * seq : 1
             */

            public String adImage;
            public String adText;
            public String adUrl;
            public int id;
            public int positionId;
            public int seq;

            public String getAdImage() {
                return adImage;
            }

            public void setAdImage(String adImage) {
                this.adImage = adImage;
            }

            public String getAdText() {
                return adText;
            }

            public void setAdText(String adText) {
                this.adText = adText;
            }

            public String getAdUrl() {
                return adUrl;
            }

            public void setAdUrl(String adUrl) {
                this.adUrl = adUrl;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getPositionId() {
                return positionId;
            }

            public void setPositionId(int positionId) {
                this.positionId = positionId;
            }

            public int getSeq() {
                return seq;
            }

            public void setSeq(int seq) {
                this.seq = seq;
            }
        }

        public static class AdSub1Bean {
            /**
             * adImage : https://img.iplaysoft.com/wp-content/uploads/2019/free-images/free_stock_photo.jpg
             * adText : 甄选限时购，全场免息GOGOGO
             * adUrl : https://www.baidu.com/
             * id : 6
             * positionId : 4
             * seq : 0
             */

            public String adImage;
            public String adText;
            public String adUrl;
            public int id;
            public int positionId;
            public int seq;

            public String getAdImage() {
                return adImage;
            }

            public void setAdImage(String adImage) {
                this.adImage = adImage;
            }

            public String getAdText() {
                return adText;
            }

            public void setAdText(String adText) {
                this.adText = adText;
            }

            public String getAdUrl() {
                return adUrl;
            }

            public void setAdUrl(String adUrl) {
                this.adUrl = adUrl;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getPositionId() {
                return positionId;
            }

            public void setPositionId(int positionId) {
                this.positionId = positionId;
            }

            public int getSeq() {
                return seq;
            }

            public void setSeq(int seq) {
                this.seq = seq;
            }
        }
    }

    public static class SubjectBean {
        /**
         * id : 16
         * product : [{"id":26,"keywords":[],"name":"华为 HUAWEI P20 ","originalPrice":4288,"pic":"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/5ac1bf58Ndefaac16.jpg","price":3788,"productSn":"6946605","stock":1000,"storeId":0,"unit":"件"},{"id":27,"keywords":[],"name":"小米8 全面屏游戏智能手机 6GB+64GB 黑色 全网通4G 双卡双待","originalPrice":2699,"pic":"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/xiaomi.jpg","price":2699,"productSn":"7437788","stock":100,"storeId":0,"unit":""},{"id":28,"keywords":[],"name":"小米 红米5A 全网通版 3GB+32GB 香槟金 移动联通电信4G手机 双卡双待","originalPrice":649,"pic":"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/5a9d248cN071f4959.jpg","price":649,"productSn":"7437789","stock":100,"storeId":0,"unit":""},{"id":29,"keywords":[],"name":"Apple iPhone 8 Plus 64GB 红色特别版 移动联通电信4G手机","originalPrice":5499,"pic":"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/5acc5248N6a5f81cd.jpg","price":5499,"productSn":"7437799","stock":100,"storeId":0,"unit":""},{"id":30,"keywords":[],"name":"HLA海澜之家简约动物印花短袖T恤","originalPrice":98,"pic":"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/5ad83a4fN6ff67ecd.jpg!cc_350x449.jpg","price":98,"productSn":"HNTBJ2E042A","stock":100,"storeId":0,"unit":""},{"id":31,"keywords":[],"name":"HLA海澜之家蓝灰花纹圆领针织布短袖T恤","originalPrice":98,"pic":"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/5ac98b64N70acd82f.jpg!cc_350x449.jpg","price":98,"productSn":"HNTBJ2E080A","stock":100,"storeId":0,"unit":""},{"id":35,"keywords":[],"name":"耐克NIKE 男子 休闲鞋 ROSHE RUN 运动鞋 511881-010黑色41码","originalPrice":369,"pic":"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/5b235bb9Nf606460b.jpg","price":369,"productSn":"6799342","stock":100,"storeId":0,"unit":""},{"id":36,"keywords":[],"name":"耐克NIKE 男子 气垫 休闲鞋 AIR MAX 90 ESSENTIAL 运动鞋 AJ1285-101白色41码","originalPrice":499,"pic":"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/5b19403eN9f0b3cb8.jpg","price":499,"productSn":"6799345","stock":100,"storeId":0,"unit":""},{"id":32,"keywords":[],"name":"HLA海澜之家短袖T恤男基础款","originalPrice":68,"pic":"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/5a51eb88Na4797877.jpg","price":68,"productSn":"HNTBJ2E153A","stock":100,"storeId":0,"unit":""},{"id":33,"keywords":[],"name":"小米（MI）小米电视4A ","originalPrice":2499,"pic":"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/5b02804dN66004d73.jpg","price":2499,"productSn":"4609652","stock":100,"storeId":0,"unit":""},{"id":34,"keywords":[],"name":"小米（MI）小米电视4A 65英寸","originalPrice":3999,"pic":"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/5b028530N51eee7d4.jpg","price":3999,"productSn":"4609660","stock":100,"storeId":0,"unit":""}]
         * title : 超值热卖
         */

        public int id;
        public String title;
        public List<ProductBean> product;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<ProductBean> getProduct() {
            return product;
        }

        public void setProduct(List<ProductBean> product) {
            this.product = product;
        }

        public static class ProductBean {
            /**
             * id : 26
             * keywords : []
             * name : 华为 HUAWEI P20
             * originalPrice : 4288
             * pic : http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/5ac1bf58Ndefaac16.jpg
             * price : 3788
             * productSn : 6946605
             * stock : //有库存1 无库存0
             * storeId : 0
             * unit : 件
             * salePrice : 1234
             * productSlogan :  [
             * {
             * "icon": "imageurl",
             * "text": "支付分期支付，自费率0.0003%/日"
             * },
             * {
             * "icon": "imageurl",
             * "text": "闲置吃灰可寄卖，￥999.00秒到账"
             * }
             * ]
             * productDetailUrl ： //分期产品详情页跳转URL
             */

            public int id;
            public String name;
            public String originalPrice;
            public String pic;
            public int price;
            public String productSn;
            public int stock;
            public int storeId;
            public String unit;
            public String salePrice;
            public List<String> keywords;
            public List<ProductSloganBean> productSlogan;
            public String productDetailUrl; //

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getOriginalPrice() {
                return originalPrice;
            }

            public void setOriginalPrice(String originalPrice) {
                this.originalPrice = originalPrice;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public String getProductSn() {
                return productSn;
            }

            public void setProductSn(String productSn) {
                this.productSn = productSn;
            }

            public int getStock() {
                return stock;
            }

            public void setStock(int stock) {
                this.stock = stock;
            }

            public int getStoreId() {
                return storeId;
            }

            public void setStoreId(int storeId) {
                this.storeId = storeId;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public String getSalePrice() {
                return salePrice;
            }

            public void setSalePrice(String salePrice) {
                this.salePrice = salePrice;
            }

            public List<String> getKeywords() {
                return keywords;
            }

            public void setKeywords(List<String> keywords) {
                this.keywords = keywords;
            }

            public List<ProductSloganBean> getProductSlogan() {
                return productSlogan;
            }

            public void setProductSlogan(List<ProductSloganBean> productSlogan) {
                this.productSlogan = productSlogan;
            }

            public static class ProductSloganBean {
                /**
                 * icon : imageurl
                 * text : 支付分期支付，自费率0.0003%/日
                 */

                public String icon;
                public String text;

                public String getIcon() {
                    return icon;
                }

                public void setIcon(String icon) {
                    this.icon = icon;
                }

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }
            }
        }
    }

    public static class CategoryBean {
        /**
         * id : 1
         * keywords : 服装
         * level : 0
         * name : 服装
         * navStatus : 1
         * parentId : 0
         * productCount : 100
         * productUnit : 件
         * showStatus : 1
         * sort : 1
         * icon : http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20190129/subject_cate_jiadian.png
         * type : "", 类型
         */

        public String id;
        public String keywords;
        public int level;
        public String name;
        public int navStatus;
        public int parentId;
        public int productCount;
        public String productUnit;
        public int showStatus;
        public int sort;
        public String icon;
        public String type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getNavStatus() {
            return navStatus;
        }

        public void setNavStatus(int navStatus) {
            this.navStatus = navStatus;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public int getProductCount() {
            return productCount;
        }

        public void setProductCount(int productCount) {
            this.productCount = productCount;
        }

        public String getProductUnit() {
            return productUnit;
        }

        public void setProductUnit(String productUnit) {
            this.productUnit = productUnit;
        }

        public int getShowStatus() {
            return showStatus;
        }

        public void setShowStatus(int showStatus) {
            this.showStatus = showStatus;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }

    @Override
    public String toString() {
        return "HomePageInfoBean{" +
                "position=" + position +
                ", subject=" + subject +
                ", category=" + category +
                '}';
    }
}
