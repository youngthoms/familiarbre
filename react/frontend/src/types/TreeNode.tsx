export interface TreeNode {
    id: string | number;
    pids?: Array<number>;
    mid?: string | number;
    fid?: string | number;
    name: string;
    img?: string;
}