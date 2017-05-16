public class BowlingGame {

    static private String strike = "X";
    static private String spare = "[1-9]/";
    static private String miss1 = "-[1-9]";
    static private String miss2 = "[1-9]-";

    private static int ParseInt1st(String part) {
        return Integer.parseInt(part.substring(0, 1));
    }

    private static int ParseInt2nd(String code) {
        return Integer.parseInt(code.substring(1, 2));
    }

    public static int computeScore(String[] codes) { //对除了倒数两击进行计算
        int score = 0;
        for (int i = 0; i != codes.length - 2; ++i) {
            if (codes[i].matches(strike)) {    //第一球是strike
                score += 10;
                //统计后两球的分数
                if (codes[i + 1].matches(strike)) { //下一个是strike
                    score += 10;
                    if (codes[i + 2].matches(strike)) {//接着第二个也是strike
                        score += 10;
                    } else if (codes[i + 2].matches(spare)) {
                        score += (ParseInt1st(codes[i + 1]));
                    } else if (codes[i + 2].matches(miss1)) {
                        score += 0;
                    } else if (codes[i + 2].matches(miss2)) {
                        score += (ParseInt1st(codes[i + 1]));
                    } else {
                        System.out.println("error strike strike ");
                    }
                } else if (codes[i + 1].matches(spare)) {
                    score += 10; //spare的情况，一定是加10
                } else if (codes[i + 1].matches(miss1)) {
                    score += (ParseInt2nd(codes[i + 1]));
                } else if (codes[i + 1].matches(miss2)) {
                    score += (ParseInt1st(codes[i + 1]));
                } else {
                    System.out.println("error strike");
                }

            } else if (codes[i].matches(spare)) { //spare且不是不是最后一击
                score += 10;
                if (codes[i + 1].matches(strike)) {
                    score += 10;
                } else if (codes[i + 1].matches(spare) || codes[i + 1].matches(miss2)) {
                    score += (ParseInt1st(codes[i + 1]));
                } else if (codes[i + 1].matches(miss1)) {
                    score += 0; //写出来更简洁明了
                } else {
                    System.out.println("error spare");
                }

            } else if (codes[i].matches(miss1)) {
                score += ParseInt2nd(codes[i]);
            } else if (codes[i].matches(miss2)) {
                score += (ParseInt1st(codes[i]));
            } else {
                System.out.println("error all");
            }
        }
        return score;
    }

    public int getBowlingScore(String bowlingCode) {
        int score = 0;
        String[] parts = bowlingCode.split("\\|\\|"); //先将数据分为两部分,再对第一部分(10个单元格计算)
        String[] codes = parts[0].split("\\|");
        score = computeScore(codes); //计算除了倒数两下的分数
        boolean hasExtra = parts.length == 2 ? true : false;
        int last2ed = codes.length - 2;
        int last1st = codes.length - 1;
        if (hasExtra) { //有extra的类型
            if (codes[last2ed].matches(strike) && codes[last1st].matches(strike)) { //strike strike
                score += 10;//倒数第二击
                score += 10;
                score += (ParseInt1st(parts[1]));
                score += 10; //倒数第一击
                score += ParseInt1st(parts[1]);
                score += (ParseInt2nd(parts[1]));
            } else if (codes[last2ed].matches(strike) && codes[last1st].matches(spare)) {//strike spare
                score += 10; //倒数第二
                score += 10;
                score += 10; //最后
                score += (ParseInt1st(parts[1]));
            } else if (codes[last2ed].matches(spare) && codes[last1st].matches(strike)) { //spare strike
                score += 10; //倒数第二
                score += 10;
                score += (ParseInt1st(parts[1]));
                score += 10;//最后
                score += (ParseInt1st(parts[1]));
                score += (ParseInt2nd(parts[1]));

            } else if (codes[last2ed].matches(spare) && codes[last1st].matches(spare)) { //spare sprea
                score += 10; //倒数第二
                score += (ParseInt1st(codes[last1st]));
                score += 10;//最后
                score += (ParseInt1st(parts[1]));
            } else if (codes[last2ed].matches(miss1) && codes[last1st].matches(spare)) {
                score += (ParseInt2nd(codes[last2ed])); //2
                score += 10; //1
                score += (ParseInt1st(parts[1]));
            } else if (codes[last2ed].matches(miss1) && codes[last1st].matches(strike)) {
                score += (ParseInt2nd(codes[last2ed])); //2
                score += 10; //1
                score += (ParseInt1st(parts[1]));
                score += (ParseInt2nd(parts[1]));
            } else if (codes[last2ed].matches(miss2) && codes[last1st].matches(spare)) {
                score += (ParseInt1st(codes[last2ed])); //2
                score += 10; //1
                score += (ParseInt1st(parts[1]));

            } else if (codes[last2ed].matches(miss2) && codes[last1st].matches(strike)) {
                score += (ParseInt1st(codes[last2ed])); //2
                score += 10; //1
                score += (ParseInt1st(parts[1]));
                score += (ParseInt2nd(parts[1]));
            } else {
                System.out.println("error 3");
            }
        } else { //没有extra的类型
            //统计倒数第二的分数
            if (codes[last2ed].matches(strike)) { //倒数第二是strike
                score += 10;
                if (codes[last1st].matches(miss1)) { //最后一击只可能是miss1或者miss2
                    score += (ParseInt2nd(codes[last1st]));
                } else if (codes[last1st].matches(miss2)) {
                    score += (ParseInt1st(codes[last1st]));
                } else {
                    System.out.println("error last two 1");
                }
            } else if (codes[last2ed].matches(spare)) {
                score += 10;
                if (codes[last1st].matches(miss1)) {
                    score += 0;
                } else if (codes[last1st].matches(miss2)) {
                    score += (ParseInt1st(codes[last1st]));
                } else {
                    System.out.println("error last two 2");
                }

            } else if (codes[last2ed].matches(miss1)) {
                score += (ParseInt2nd(codes[last2ed]));

            } else if (codes[last2ed].matches(miss2)) {
                score += (ParseInt1st(codes[last2ed]));
            } else {
                System.out.println("error last two");
            }

            //统计最后一下的分数
            if (codes[last1st].matches(miss1)) {
                score += (ParseInt2nd(codes[last1st]));
            } else if (codes[last1st].matches(miss2)) {
                score += (ParseInt1st(codes[last1st]));
            }

        }

        return score;
    }


}
