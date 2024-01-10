export interface TreeNode {
    id: string | number;
    pids?: Array<number | string>;
    mid?: string | number;
    fid?: string | number;
    name: string;
    securitySocialNumber: string;
}