import {
    SHOW_HEADER,
    HIDE_HEADER
} from './types'

export const showHeader = () => {
    return { type: SHOW_HEADER }
}

export const hideHeader = () => {
    return { type: HIDE_HEADER }
}